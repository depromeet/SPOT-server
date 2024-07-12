package org.depromeet.spot.common.exception.stadium;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class StadiumException extends BusinessException {

    protected StadiumException(StadiumErrorCode errorCode) {
        super(errorCode);
    }

    public static class StadiumNotFoundException extends StadiumException {
        public StadiumNotFoundException() {
            super(StadiumErrorCode.STADIUM_NOT_FOUND);
        }

        public StadiumNotFoundException(String str) {
            super(StadiumErrorCode.STADIUM_NOT_FOUND.appended(str));
        }
    }
}
