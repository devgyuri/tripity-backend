package me.gyuri.tripity.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.auth.dto.CreateAccessTokenRequest;
import me.gyuri.tripity.domain.auth.dto.CreateAccessTokenResponse;
import me.gyuri.tripity.domain.auth.service.TokenService;
import me.gyuri.tripity.global.config.jwt.TokenProvider;
import me.gyuri.tripity.global.util.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@Slf4j
public class TokenController {
    private final TokenService tokenService;
    private final TokenProvider tokenProvider;

    @PostMapping("/api/auth/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(HttpServletRequest request, HttpServletResponse response, @CookieValue("refresh_token") String refreshToken) {
        log.info("+++++ token controller +++++");
        log.info("refresh token (cookie): {}", refreshToken);

        if (!tokenProvider.validToken(refreshToken)) {
            CookieUtil.deleteCookie(request, response, "refresh_token");
            throw new IllegalArgumentException("Invalid token");
        }

        String newAccessToken = tokenService.createNewAccessToken(refreshToken);

        String newRefreshToken = tokenService.createNewRefreshToken(refreshToken);
        CookieUtil.deleteCookie(request, response, "refresh_token");
        CookieUtil.addCookie(response, "refresh_token", newRefreshToken, (int) Duration.ofDays(14).toSeconds());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
