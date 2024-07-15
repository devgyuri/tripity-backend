package me.gyuri.tripity.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProviderType {
    LOCAL("LOCAL"),
    NAVER("NAVER"),
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");

    private final String value;
}
