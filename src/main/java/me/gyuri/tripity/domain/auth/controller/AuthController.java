package me.gyuri.tripity.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.auth.dto.MailRequest;
import me.gyuri.tripity.domain.auth.dto.RestoreTokenResponse;
import me.gyuri.tripity.domain.auth.dto.SignupRequest;
import me.gyuri.tripity.domain.auth.dto.VerifyRequest;
import me.gyuri.tripity.domain.auth.service.AuthService;
import me.gyuri.tripity.domain.auth.service.TokenService;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.repository.UserRepository;
import me.gyuri.tripity.global.util.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/api/auth/verify/send")
    public ResponseEntity<Void> sendVerificationCode(@RequestBody @Valid MailRequest request) throws Exception {
        authService.sendVerificationCode(request);

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/api/auth/verify/check")
    public ResponseEntity<Boolean> checkVerificationCode(@RequestBody VerifyRequest request) throws Exception {
        boolean isVerified = authService.checkVerificationCode(request);

        return ResponseEntity.ok()
                .body(isVerified);
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Long> signUp(@RequestBody @Valid SignupRequest request) {
        Long userId = authService.signUp(request);

        return ResponseEntity.ok()
                .body(userId);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> deleteRefreshToken(HttpSession session, HttpServletRequest request, HttpServletResponse response, @CookieValue("refresh_token") String refreshToken) {
        session.invalidate();

        tokenService.deleteRefreshToken(refreshToken);
        CookieUtil.deleteCookie(request, response, "refresh_token");

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/api/auth/token")
    public ResponseEntity<RestoreTokenResponse> restoreToken(HttpServletRequest request, HttpServletResponse response, @CookieValue("refresh_token") String refreshToken) {
        tokenService.validateToken(request, response, refreshToken);

        User user = tokenService.findUser(refreshToken);

        String newAccessToken = tokenService.createNewAccessToken(refreshToken);
        String newRefreshToken = tokenService.createNewRefreshToken(refreshToken);

        RestoreTokenResponse result = tokenService.sendAccessTokenAndUserInfo(request, response, newAccessToken, user);
        tokenService.sendRefreshToken(request, response, newRefreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }
}
