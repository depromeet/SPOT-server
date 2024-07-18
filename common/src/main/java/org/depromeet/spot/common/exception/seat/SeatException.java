package org.depromeet.spot.common.exception.seat;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class SeatException extends BusinessException {

    protected SeatException(SeatErrorCode errorCode) {
        super(errorCode);
    }

    public static class SeatNotFoundException extends SeatException {
        public SeatNotFoundException() {
            super(SeatErrorCode.SEAT_NOT_FOUND);
        }

        public SeatNotFoundException(Object obj) {
            super(SeatErrorCode.SEAT_NOT_FOUND.appended(obj));
        }
    }
}
