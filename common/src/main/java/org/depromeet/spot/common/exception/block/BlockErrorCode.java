package org.depromeet.spot.common.exception.block;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum BlockErrorCode implements ErrorCode {
    BLOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "BL001", "요청한 블록을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    BlockErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public BlockErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
