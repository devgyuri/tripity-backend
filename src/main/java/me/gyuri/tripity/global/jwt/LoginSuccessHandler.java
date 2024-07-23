package me.gyuri.tripity.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.auth.dto.LoginResponse;
import me.gyuri.tripity.domain.auth.entity.RefreshToken;
import me.gyuri.tripity.domain.auth.repository.RefreshTokenRepository;
import me.gyuri.tripity.domain.auth.service.RefreshTokenService;
import me.gyuri.tripity.domain.auth.service.TokenService;
import me.gyuri.tripity.domain.user.dto.UserInfo;
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
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("+++++ LoginSuccessHandler +++++");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail((String) userDetails.getUsername());

        // save refresh token to cookie
        String refreshToken = tokenService.createRefreshToken(user);
        refreshTokenService.saveRefreshToken(user.getId(), refreshToken);
        tokenService.sendRefreshToken(request, response, refreshToken);

        // save access token to response
        String accessToken = tokenService.createAccessToken(user);
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .userInfo(UserInfo.from(user))
                .build();

        super.clearAuthenticationAttributes(request);

        log.info("로그인에 성공하였습니다. 이메일: {}", user.getEmail());
        log.info("로그인에 성공하였습니다. AccessToken: {}", accessToken);


        String loginResponseJson = new ObjectMapper().writeValueAsString(loginResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(loginResponseJson);
    }
}
