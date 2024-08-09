package org.depromeet.spot.application.common.exception;

import lombok.Getter;

@Getter
public class CustomJwtException extends RuntimeException {
    private final JwtErrorCode jwtErrorCode;

    public CustomJwtException(JwtErrorCode jwtErrorCode) {
        super(jwtErrorCode.getMessage());
        this.jwtErrorCode = jwtErrorCode;
    }
}
