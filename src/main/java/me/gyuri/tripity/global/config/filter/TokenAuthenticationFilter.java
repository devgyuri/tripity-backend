package me.gyuri.tripity.global.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.auth.service.TokenService;
import me.gyuri.tripity.global.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final String NO_CHECK_URL = "/login";
    private static final String NO_CHECK_URL2 = "/api/auth/signup";

    private static final List<String> EXCLUDE_URL = Collections.unmodifiableList(
            Arrays.asList(
                    "/api/auth/verify/send"
            ));

    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("+++++ token authentication filter +++++");
        if (request.getRequestURI().equals(NO_CHECK_URL) || request.getRequestURI().equals(NO_CHECK_URL2)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenService.extractAccessToken(request)
                .orElse(null);
        if (token != null) {
            log.info("token not null");
            tokenService.validateToken(request, response, token);

            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("+++++ token authentication filter +++++");
        log.info("{}", request.getServletPath());
        log.info("{}", request.getRequestURI());
        log.info("{}", EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath())));
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }
}
