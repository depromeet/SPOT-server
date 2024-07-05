package org.depromeet.spot.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatus();

    String getCode();

    String getMessage();
}
