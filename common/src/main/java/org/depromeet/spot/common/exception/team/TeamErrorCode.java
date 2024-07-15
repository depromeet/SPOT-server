package org.depromeet.spot.common.exception.team;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum TeamErrorCode implements ErrorCode {
    BASEBALL_TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "T001", "요청 구단이 존재하지 않습니다."),
    INVALID_TEAM_NAME_NOT_FOUND(HttpStatus.BAD_REQUEST, "T002", "구단명이 잘못되었습니다."),
    INVALID_TEAM_ALIAS_NOT_FOUND(HttpStatus.BAD_REQUEST, "T003", "구단 별칭이 잘못되었습니다."),
    DUPLICATE_TEAM_NAME(HttpStatus.CONFLICT, "T004", "이미 등록된 구단입니다."),
    EMPTY_TEAM_LOGO(HttpStatus.BAD_REQUEST, "T005", "구단 로고를 등록해주세요."),
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
