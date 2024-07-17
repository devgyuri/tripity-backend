package me.gyuri.tripity.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.gyuri.tripity.domain.user.dto.UserInfo;

@AllArgsConstructor
@Getter
public class CreateAccessTokenResponse {
    private String accessToken;
    private UserInfo userInfo;
}
