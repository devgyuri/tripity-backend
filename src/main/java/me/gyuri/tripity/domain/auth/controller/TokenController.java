package me.gyuri.tripity.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.auth.dto.RestoreTokenResponse;
import me.gyuri.tripity.domain.auth.service.RefreshTokenService;
import me.gyuri.tripity.domain.auth.service.TokenService;
import me.gyuri.tripity.domain.user.dto.UserInfo;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.global.util.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class TokenController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/auth/token")
    public ResponseEntity<RestoreTokenResponse> restoreToken(HttpServletRequest request, HttpServletResponse response, @CookieValue("refresh_token") String refreshToken) {
        log.info("+++++ token controller +++++");
        log.info("refresh token (cookie): {}", refreshToken);

        tokenService.validateRefreshToken(request, response, refreshToken);

        User user = tokenService.findUser(refreshToken);

        String newAccessToken = tokenService.createNewAccessToken(refreshToken);
        String newRefreshToken = tokenService.createNewRefreshToken(refreshToken);

        RestoreTokenResponse result = tokenService.sendAccessTokenAndUserInfo(request, response, newAccessToken, user);
        tokenService.sendRefreshToken(request, response, newRefreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> deleteRefreshToken(HttpSession session, HttpServletRequest request, HttpServletResponse response, @CookieValue("refresh_token") String refreshToken) {
        session.invalidate();

        refreshTokenService.delete(refreshToken);
        CookieUtil.deleteCookie(request, response, "refresh_token");

        return ResponseEntity.ok()
                .build();
    }
}
