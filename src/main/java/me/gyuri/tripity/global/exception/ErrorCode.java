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
    FAILED_SENDING_VERIFY_CODE(HttpStatus.BAD_REQUEST, "인증 코드 전송에 실패했습니다."),
    INCORRECT_VERIFY_CODE(HttpStatus.BAD_REQUEST, "인증 코드가 올바르지 않습니다."),
    EXPIRED_VERIFY_CODE(HttpStatus.BAD_REQUEST, "인증 코드가 만료되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "인증이 유효하지 않습니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NONE_CORRECT_PW(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NONE_CORRECT_EMAIL_AND_PW(HttpStatus.NOT_FOUND, "이메일 혹은 비밀번호를 다시 확인해 주세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),

    // Article
    NOT_MATCH_AUTHOR(HttpStatus.UNAUTHORIZED, "작성자가 아닙니다."),
    NOT_FOUND_ARTICLE(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
