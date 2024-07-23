package me.gyuri.tripity.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.auth.dto.MailRequest;
import me.gyuri.tripity.domain.auth.dto.NicknameRequest;
import me.gyuri.tripity.domain.auth.dto.VerifyRequest;
import me.gyuri.tripity.domain.user.dto.ProviderType;
import me.gyuri.tripity.domain.auth.dto.SignupRequest;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.repository.UserRepository;
import me.gyuri.tripity.domain.user.service.UserService;
import me.gyuri.tripity.global.exception.CustomException;
import me.gyuri.tripity.global.exception.ErrorCode;
import me.gyuri.tripity.global.redis.service.RedisService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final RedisService redisService;

    public void sendVerificationCode(MailRequest request) throws Exception {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
        mailService.sendMail(request.getEmail());
    }

    public boolean checkVerificationCode(VerifyRequest request) throws Exception {
        if (!redisService.existData(request.getEmail())) {
            throw new CustomException(ErrorCode.EXPIRED_VERIFY_CODE);
        }

        String enteredCods = request.getCode();
        String cachedCode = redisService.getData(request.getEmail());
        if (!cachedCode.equals(enteredCods)) {
            throw new CustomException(ErrorCode.INCORRECT_VERIFY_CODE);
        }
        return true;
    }

    @Transactional
    public Long signUp(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
        if (!Objects.equals(request.getPassword(), request.getRePassword())) {
            throw new CustomException(ErrorCode.NONE_CORRECT_PW);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(encoder.encode(request.getPassword()))
                .providerType(ProviderType.LOCAL)
                .build()).getId();
    }

    public boolean isNicknameAvailable(NicknameRequest request) {
        return !userRepository.existsByNickname(request.getNickname());
    }
}
