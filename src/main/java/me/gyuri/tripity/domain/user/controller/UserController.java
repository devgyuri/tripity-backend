package me.gyuri.tripity.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.auth.dto.SignupRequest;
import me.gyuri.tripity.domain.user.dto.ImageUploadRequest;
import me.gyuri.tripity.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PatchMapping("api/users/image")
    public ResponseEntity<String> uploadImage(ImageUploadRequest request) throws IOException {
        String url = userService.uploadImage(request);

        return ResponseEntity.ok()
                .body(url);
    }
}
