package me.gyuri.tripity.domain.user.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.user.dto.UpdateUserRequest;
import me.gyuri.tripity.domain.user.dto.UploadImageRequest;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.repository.UserRepository;
import me.gyuri.tripity.global.exception.CustomException;
import me.gyuri.tripity.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public String uploadImage(UploadImageRequest request) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = request.getImage().getContentType();

        String keyFileName = "steel-cursor-431612-a9-9582f54560ca.json";
        InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                request.getImage().getInputStream()
        );

        return uuid;
    }

    @Transactional
    public User updateUser(UpdateUserRequest request, String email) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!user.getNickname().equals(request.getNickname())) {
            if (isDuplicatedNickname(request.getNickname())) {
                throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
            }
        }

        user.update(request.getNickname(), request.getIntro(), request.getImage());
        return user;
    }

    public boolean isDuplicatedNickname(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }
}
