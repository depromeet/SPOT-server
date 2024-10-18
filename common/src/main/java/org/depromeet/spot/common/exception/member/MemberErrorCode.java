package org.depromeet.spot.common.exception.member;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "요청 유저가 존재하지 않습니다."),
    MEMBER_NICKNAME_CONFLICT(HttpStatus.CONFLICT, "M002", "닉네임이 중복됩니다."),
    INVALID_LEVEL(HttpStatus.INTERNAL_SERVER_ERROR, "M003", "잘못된 레벨입니다."),
    INACTIVE_MEMBER(HttpStatus.GONE, "M004", "탈퇴한 유저입니다."),
    MEMBER_CONFLICT(HttpStatus.BAD_REQUEST, "M005", "이미 가입된 유저입니다."),
    MEMBERS_NOT_EXIST(HttpStatus.NOT_FOUND, "M006", "가입한 유저가 없습니다.");

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
