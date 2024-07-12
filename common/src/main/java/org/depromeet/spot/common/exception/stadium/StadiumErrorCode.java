package org.depromeet.spot.common.exception.stadium;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum StadiumErrorCode implements ErrorCode {
    STADIUM_NOT_FOUND(HttpStatus.NOT_FOUND, "ST001", "요청 경기장이 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    StadiumErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public StadiumErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
