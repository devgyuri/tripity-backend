package me.gyuri.tripity.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NicknameRequest {
    @NotBlank
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^(?!.*\\s)[a-zA-Z0-9가-힣]*$", message = "닉네임 형식을 확인해 주세요. 공백이나 특수문자는 사용할 수 없습니다.")
    private String nickname;
}
