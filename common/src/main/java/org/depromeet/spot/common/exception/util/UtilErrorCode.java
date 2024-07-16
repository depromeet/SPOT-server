package org.depromeet.spot.common.exception.util;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum UtilErrorCode implements ErrorCode {
    INVALID_RGB_CODE(HttpStatus.BAD_REQUEST, "UT001", "잘못된 RGB 코드 값 입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    UtilErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public UtilErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
