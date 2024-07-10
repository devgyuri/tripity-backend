package me.gyuri.tripity.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.user.dto.CreateUserRequest;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(CreateUserRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(encoder.encode(request.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
