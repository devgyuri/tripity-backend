package me.gyuri.tripity.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.user.dto.ImageUploadRequest;
import me.gyuri.tripity.domain.user.dto.UpdateUserRequest;
import me.gyuri.tripity.domain.user.dto.UserInfo;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PutMapping("/api/users/profile")
    public ResponseEntity<UserInfo> updateProfile(UpdateUserRequest request, Principal principal) throws IOException {
//    public ResponseEntity<User> updateProfile(Principal principal) throws IOException {
        log.info("+++++ update profile +++++");
        log.info(principal.getName()); // email

        User updatedUser = userService.updateUser(request, principal.getName());

        return ResponseEntity.ok()
                .body(UserInfo.from(updatedUser));
    }

    @PutMapping("/api/users/image")
    public ResponseEntity<String> uploadImage(ImageUploadRequest request) throws IOException {
        String url = userService.uploadImage(request);

        return ResponseEntity.ok()
                .body(url);
    }
}
