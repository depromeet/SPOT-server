package org.depromeet.spot.application.common.exception;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JwtErrorCode implements ErrorCode {
    NONEXISTENT_TOKEN(HttpStatus.UNAUTHORIZED, "JWT001", "해당 요청은 Jwt 토큰이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT002", "잘못된 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT003", "만료된 토큰입니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "JWT004", "사용자가 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
