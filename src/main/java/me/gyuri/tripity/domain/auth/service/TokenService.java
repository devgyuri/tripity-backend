package me.gyuri.tripity.domain.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.auth.dto.RestoreTokenResponse;
import me.gyuri.tripity.domain.auth.entity.RefreshToken;
import me.gyuri.tripity.domain.user.dto.UserInfo;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.service.UserService;
import me.gyuri.tripity.global.config.jwt.TokenProvider;
import me.gyuri.tripity.global.exception.CustomException;
import me.gyuri.tripity.global.exception.ErrorCode;
import me.gyuri.tripity.global.util.CookieUtil;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenService {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public User findUser(String refreshToken) {
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        return userService.findById(userId);
    }

    public void validateToken(HttpServletRequest request, HttpServletResponse response, String token) {
        ErrorCode e = tokenProvider.validToken(token);
        if (e != null) {
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
            throw new CustomException(e);
        }
    }

    public String createAccessToken(User user) {
        return tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
    }

    public String createRefreshToken(User user) {
        return tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
    }

    public String createNewAccessToken(String refreshToken) {
        User user = findUser(refreshToken);

        return createAccessToken(user);
    }

    @Transactional
    public String createNewRefreshToken(String refreshToken) {
        RefreshToken oldRefreshToken = refreshTokenService.findByRefreshToken(refreshToken);
        User user = findUser(refreshToken);
        String newRefreshToken = createRefreshToken(user);
        oldRefreshToken.update(newRefreshToken);

        return newRefreshToken;
    }

    public RestoreTokenResponse sendAccessTokenAndUserInfo(HttpServletRequest request, HttpServletResponse response, String accessToken, User user) {
        UserInfo userInfo = UserInfo.from(user);
        return new RestoreTokenResponse(accessToken, userInfo);
    }

    public void sendRefreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, (int) REFRESH_TOKEN_DURATION.toSeconds());
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HEADER_AUTHORIZATION))
                .filter(token -> token.startsWith(TOKEN_PREFIX))
                .map(token -> token.replace(TOKEN_PREFIX, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String refreshToken = null;
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
                refreshToken = cookie.getValue();
            }
        }
        return Optional.ofNullable(refreshToken);
    }
}
