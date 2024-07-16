package me.gyuri.tripity.global.config.oauth;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.user.dto.ProviderType;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.repository.UserRepository;
import me.gyuri.tripity.global.config.oauth.info.OAuth2UserInfo;
import me.gyuri.tripity.global.config.oauth.info.OAuth2UserInfoFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
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
        String nickname = userInfo.getName() + UUID.randomUUID();
        return userRepository.save(User.builder()
                .nickname(nickname)
                .email(userInfo.getEmail())
                .providerType(providerType)
                .build());
    }

    // 사용자 이름 unique하게
    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
//        User user = userRepository.findByEmail(userInfo.getEmail())
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name + UUID.randomUUID().toString().replace("-", "").substring(0, 5))
                        .build());
        return userRepository.save(user);
    }
}
