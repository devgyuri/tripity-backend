package me.gyuri.tripity.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.gyuri.tripity.domain.user.dto.Profile;
import me.gyuri.tripity.domain.user.entity.User;

@AllArgsConstructor
@Getter
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Profile profile;
}
