package org.depromeet.spot.common.exception.media;

import org.depromeet.spot.common.exception.BusinessException;

public abstract class MediaException extends BusinessException {

    protected MediaException(MediaErrorCode errorCode) {
        super(errorCode);
    }

    public static class InvalidExtensionException extends MediaException {
        public InvalidExtensionException() {
            super(MediaErrorCode.INVALID_EXTENSION);
        }

        public InvalidExtensionException(final String s) {
            super(MediaErrorCode.INVALID_EXTENSION.appended(s));
        }
    }

    public static class InvalidStadiumMediaException extends MediaException {
        public InvalidStadiumMediaException() {
            super(MediaErrorCode.INVALID_STADIUM_MEDIA);
        }
    }

    public static class InvalidReviewMediaException extends MediaException {
        public InvalidReviewMediaException() {
            super(MediaErrorCode.INVALID_REVIEW_MEDIA);
        }
    }

    public static class InvalidMediaException extends MediaException {
        public InvalidMediaException() {
            super(MediaErrorCode.INVALID_MEDIA);
        }
    }

    public static class UploadFailException extends MediaException {
        public UploadFailException() {
            super(MediaErrorCode.UPLOAD_FAIL);
        }
    }
}
