package org.depromeet.spot.common.exception.section;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum SectionErrorCode implements ErrorCode {
    SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "SE001", "요청 구역이 존재하지 않습니다."),
    SECTION_NOT_BELONG_TO_STADIUM(HttpStatus.BAD_REQUEST, "SE002", "요청 경기장의 구역이 아닙니다."),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "SE003", "요청 경기장에 이미 해당 이름의 구역이 존재합니다."),
    DUPLICATE_ALIAS(HttpStatus.BAD_REQUEST, "SE004", "요청 경기장에 이미 해당 별칭의 구역이 존재합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    SectionErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public SectionErrorCode appended(Object o) {
        message = message + " {" + o.toString() + "}";
        return this;
    }
}
