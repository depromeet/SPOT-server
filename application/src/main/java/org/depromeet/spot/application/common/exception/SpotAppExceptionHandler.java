package org.depromeet.spot.application.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.depromeet.spot.common.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(CustomJwtException.class)
    protected ResponseEntity<ErrorResponse> handleCustomJwtException(CustomJwtException e) {
        var code = e.getJwtErrorCode().getCode();
        var message = e.getJwtErrorCode().getMessage();
        var httpStatus = e.getJwtErrorCode().getStatus();

        log.error(EXCEPTION_LOG_TEMPLATE, code, message, e);
        var response = ErrorResponse.from(e);

        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> {
                            errors.put(error.getField(), error.getDefaultMessage());
                        });
        return ResponseEntity.status(e.getStatusCode()).body(errors);
    }
}
