package me.gyuri.tripity.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoreTokenRequest {
    private String refreshToken;
}
