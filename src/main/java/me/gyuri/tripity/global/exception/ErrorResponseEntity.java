package me.gyuri.tripity.global.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    private int status;
    private String name;
    private String message;

    public static ErrorResponseEntity toEntity(ErrorCode e) {
        return ErrorResponseEntity.builder()
                .status(e.getHttpStatus().value())
                .name(e.name())
                .message(e.getDetail())
                .build();
    }

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .message(e.getDetail())
                        .build());
    }
}
