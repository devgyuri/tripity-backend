package me.gyuri.tripity.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.auth.entity.RefreshToken;
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

    public String createNewAccessToken(String refreshToken) {
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(1));
    }

    @Transactional
    public String createNewRefreshToken(String refreshToken) {
        RefreshToken oldRefreshToken = refreshTokenService.findByRefreshToken(refreshToken);
        Long userId = oldRefreshToken.getUserId();
        User user = userService.findById(userId);
        String newRefreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));
        oldRefreshToken.update(newRefreshToken);

        return newRefreshToken;
    }
}
