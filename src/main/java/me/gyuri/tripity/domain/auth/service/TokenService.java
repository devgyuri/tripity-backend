package me.gyuri.tripity.domain.auth.service;

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
import me.gyuri.tripity.global.util.CookieUtil;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public User findUser(String refreshToken) {
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        return userService.findById(userId);
    }

    public void validateRefreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) throws IllegalArgumentException {
        if (!tokenProvider.validToken(refreshToken)) {
            CookieUtil.deleteCookie(request, response, "refresh_token");
            throw new IllegalArgumentException("Invalid token");
        }
    }

    public String createNewAccessToken(String refreshToken) {
        User user = findUser(refreshToken);

        return tokenProvider.generateToken(user, Duration.ofHours(1));
    }

    @Transactional
    public String createNewRefreshToken(String refreshToken) {
        RefreshToken oldRefreshToken = refreshTokenService.findByRefreshToken(refreshToken);
        User user = findUser(refreshToken);
        String newRefreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));
        oldRefreshToken.update(newRefreshToken);

        return newRefreshToken;
    }

    public RestoreTokenResponse sendAccessTokenAndUserInfo(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        User user = findUser(refreshToken);
        UserInfo userInfo = UserInfo.from(user);

        String newAccessToken = createNewAccessToken(refreshToken);

        return new RestoreTokenResponse(newAccessToken, userInfo);
    }

    public void sendRefreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        String newRefreshToken = createNewRefreshToken(refreshToken);
        CookieUtil.deleteCookie(request, response, "refresh_token");
        CookieUtil.addCookie(response, "refresh_token", newRefreshToken, (int) Duration.ofDays(14).toSeconds());
    }
}
