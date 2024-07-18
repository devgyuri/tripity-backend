package me.gyuri.tripity.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Auth
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    NONE_CORRECT_PW(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NONE_CORRECT_EMAIL_AND_PW(HttpStatus.NOT_FOUND, "이메일 혹은 비밀번호를 다시 확인해 주세요."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
