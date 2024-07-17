package org.depromeet.spot.common.exception.media;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MediaErrorCode implements ErrorCode {
    INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "ME001", "허용하지 않는 확장자입니다."),
    INVALID_STADIUM_MEDIA(HttpStatus.BAD_REQUEST, "ME002", "경기장과 관련된 미디어 파일이 아닙니다."),
    INVALID_REVIEW_MEDIA(HttpStatus.BAD_REQUEST, "ME003", "리뷰와 관련된 미디어 파일이 아닙니다."),
    INVALID_MEDIA(HttpStatus.INTERNAL_SERVER_ERROR, "ME004", "잘못된 미디어 형식입니다."),
    UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "ME005", "파일 업로드에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    public MediaErrorCode appended(final String s) {
        message = message + " {" + s + "}";
        return this;
    }
}
