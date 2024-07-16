package org.depromeet.spot.common.exception.review;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class ReviewException extends BusinessException {
    protected ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }

    public static class ReviewNotFoundException extends ReviewException {
        public ReviewNotFoundException() {
            super(ReviewErrorCode.REVIEW_NOT_FOUND);
        }

        public ReviewNotFoundException(String s) {
            super(ReviewErrorCode.REVIEW_NOT_FOUND.appended(s));
        }
    }

    public static class InvalidReviewDataException extends ReviewException {
        public InvalidReviewDataException() {
            super(ReviewErrorCode.INVALID_REVIEW_DATA);
        }

        public InvalidReviewDataException(String str) {
            super(ReviewErrorCode.INVALID_REVIEW_DATA.appended(str));
        }
    }
}
