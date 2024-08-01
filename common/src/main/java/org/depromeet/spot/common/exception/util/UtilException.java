package org.depromeet.spot.common.exception.util;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class UtilException extends BusinessException {
    protected UtilException(UtilErrorCode errorCode) {
        super(errorCode);
    }

    public static class InvalidHexCodeException extends UtilException {
        public InvalidHexCodeException() {
            super(UtilErrorCode.INVALID_HEX_CODE);
        }
    }
}
