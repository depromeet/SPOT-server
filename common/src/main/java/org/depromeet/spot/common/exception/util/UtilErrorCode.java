package org.depromeet.spot.common.exception.util;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum UtilErrorCode implements ErrorCode {
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
