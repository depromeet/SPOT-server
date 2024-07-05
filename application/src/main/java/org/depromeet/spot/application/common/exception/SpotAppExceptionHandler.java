package org.depromeet.spot.application.common.exception;

import org.depromeet.spot.common.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class SpotAppExceptionHandler {

    private static final String EXCEPTION_LOG_TEMPLATE = "code = {}, message = {}";

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        var code = e.getCode();
        var message = e.getMessage();
        var httpStatus = e.getHttpStatus();

        log.error(EXCEPTION_LOG_TEMPLATE, code, message);
        var response = ErrorResponse.from(e);

        return ResponseEntity.status(httpStatus).body(response);
    }
}
