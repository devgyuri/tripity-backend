package me.gyuri.tripity.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.auth.dto.LoginResponse;
import me.gyuri.tripity.domain.auth.entity.RefreshToken;
import me.gyuri.tripity.domain.auth.repository.RefreshTokenRepository;
import me.gyuri.tripity.domain.user.dto.Profile;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.service.UserService;
import me.gyuri.tripity.global.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("+++++ LoginSuccessHandler +++++");
        // 문제 발생할 수도? getUsername에 email이 잘 들어올 것인가?
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail((String) userDetails.getUsername());

        // save refresh token to cookie
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshToken(user.getId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        // save access token to cookie
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        addAccessTokenToCookie(request, response, accessToken);

        super.clearAuthenticationAttributes(request);

        log.info("로그인에 성공하였습니다. 이메일: {}", user.getEmail());
        log.info("로그인에 성공하였습니다. AccessToken: {}", accessToken);

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .profile(Profile.from(user))
                .build();

        String loginResponseJson = new ObjectMapper().writeValueAsString(loginResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(loginResponseJson);
    }

    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void addAccessTokenToCookie(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        int cookieMaxAge = (int) ACCESS_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, accessToken, cookieMaxAge);
    }
}
