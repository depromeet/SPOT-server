package org.depromeet.spot.common.exception.review;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ReviewErrorCode implements ErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "RV001", "요청한 리뷰를 찾을 수 없습니다."),
    INVALID_REVIEW_DATA(HttpStatus.BAD_REQUEST, "RV002", "유효하지 않은 리뷰 데이터입니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ReviewErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ReviewErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
