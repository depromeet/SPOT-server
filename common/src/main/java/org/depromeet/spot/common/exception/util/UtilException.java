package org.depromeet.spot.common.exception.util;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class UtilException extends BusinessException {
    protected UtilException(UtilErrorCode errorCode) {
        super(errorCode);
    }
}
