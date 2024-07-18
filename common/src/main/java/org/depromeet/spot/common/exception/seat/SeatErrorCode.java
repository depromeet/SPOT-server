package org.depromeet.spot.common.exception.seat;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum SeatErrorCode implements ErrorCode {
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "SEAT001", "요청 좌석이 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    SeatErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public SeatErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
