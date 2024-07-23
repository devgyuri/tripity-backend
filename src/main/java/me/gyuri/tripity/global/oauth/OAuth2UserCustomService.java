package me.gyuri.tripity.global.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.user.dto.ProviderType;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.repository.UserRepository;
import me.gyuri.tripity.global.oauth.info.OAuth2UserInfo;
import me.gyuri.tripity.global.oauth.info.OAuth2UserInfoFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("+++++ OAuth service +++++");
        OAuth2User user = super.loadUser(userRequest);
        return process(userRequest, user);
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(
                userRequest.getClientRegistration().getRegistrationId().toUpperCase()
        );
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        User savedUser = userRepository.findByEmail(userInfo.getEmail()).orElse(null);
        if (savedUser == null) {
            createUser(userInfo, providerType);
        }

        return user;
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        String nickname = userInfo.getName() + UUID.randomUUID().toString().substring(0, 8);
        return userRepository.save(User.builder()
                .nickname(nickname)
                .email(userInfo.getEmail())
                .providerType(providerType)
                .build());
    }
}
