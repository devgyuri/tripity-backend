package me.gyuri.tripity.domain.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyRequest {
    private String email;
    private String code;
}
