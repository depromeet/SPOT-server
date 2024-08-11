package org.depromeet.spot.application.common.exception;

import org.depromeet.spot.common.exception.BusinessException;

public record ErrorResponse(String code, String message) {

    public static ErrorResponse from(BusinessException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

    public static ErrorResponse from(CustomJwtException e) {
        return new ErrorResponse(e.getJwtErrorCode().getCode(), e.getJwtErrorCode().getMessage());
    }
}
