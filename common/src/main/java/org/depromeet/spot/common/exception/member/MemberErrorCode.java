package org.depromeet.spot.common.exception.member;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "요청 유저가 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    MemberErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public MemberErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
