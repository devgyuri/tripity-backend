package me.gyuri.tripity.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.user.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserInfo {
    private Long id;
    private String email;
    private String nickname;
    private String intro;
    private String image;

    public static UserInfo from(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .intro(user.getIntro())
                .image(user.getImage())
                .build();
    }
}
