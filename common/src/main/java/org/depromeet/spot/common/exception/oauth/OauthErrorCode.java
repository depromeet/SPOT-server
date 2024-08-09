package org.depromeet.spot.common.exception.oauth;

import org.depromeet.spot.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthErrorCode implements ErrorCode {
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "Oauth001", "올바르지 않은 액세스 토큰입니다. 액세스 토큰은 1회용입니다."),
    INTERNAL_OAUTH_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Oauth002",
            "Oauth 로그인 서버 오류입니다. 잠시 후 다시 시도해주세요. 반복되면 Oauth 서버에 문의바랍니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;
}
