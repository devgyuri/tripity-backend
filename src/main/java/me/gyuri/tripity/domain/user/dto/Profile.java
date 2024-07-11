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
public class Profile {
    private Long id;
    private String nickname;

    public static Profile from(User user) {
        return Profile.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
