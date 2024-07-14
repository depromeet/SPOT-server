package org.depromeet.spot.common.exception.team;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum TeamErrorCode implements ErrorCode {
    BASEBALL_TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "T001", "요청 구단이 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    TeamErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public TeamErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
