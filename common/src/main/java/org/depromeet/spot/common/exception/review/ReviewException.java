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

    public static class UnauthorizedReviewModificationException extends ReviewException {
        public UnauthorizedReviewModificationException() {
            super(ReviewErrorCode.UNAUTHORIZED_REVIEW_MODIFICATION_EXCEPTION);
        }

        public UnauthorizedReviewModificationException(String str) {
            super(ReviewErrorCode.UNAUTHORIZED_REVIEW_MODIFICATION_EXCEPTION.appended(str));
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

    public static class InvalidReviewDateTimeFormatException extends ReviewException {
        public InvalidReviewDateTimeFormatException() {
            super(ReviewErrorCode.INVALID_REVIEW_DATETIME_FORMAT);
        }

        public InvalidReviewDateTimeFormatException(String str) {
            super(ReviewErrorCode.INVALID_REVIEW_DATETIME_FORMAT.appended(str));
        }
    }

    public static class InvalidReviewKeywordsException extends ReviewException {
        public InvalidReviewKeywordsException() {
            super(ReviewErrorCode.INVALID_REVIEW_KEYWORDS);
        }

        public InvalidReviewKeywordsException(String str) {
            super(ReviewErrorCode.INVALID_REVIEW_KEYWORDS.appended(str));
        }
    }

    public static class InvalidReviewLikesException extends ReviewException {
        public InvalidReviewLikesException() {
            super(ReviewErrorCode.INVALID_REVIEW_LIKES);
        }
    }
}
