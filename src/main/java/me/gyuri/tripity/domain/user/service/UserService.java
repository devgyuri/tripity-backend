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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(CreateUserRequest request) {
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build()).getId();
    }
}
