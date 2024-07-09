package org.depromeet.spot.domain.media;

import org.depromeet.spot.common.exception.media.MediaException.InvalidMediaException;

import lombok.Getter;

@Getter
public class Media {

    private final String url;
    private final String fileName;

    public Media(final String url, final String fileName) {
        checkIsValidMedia(url, fileName);
        this.url = url;
        this.fileName = fileName;
    }

    private void checkIsValidMedia(final String url, final String fileName) {
        if (isBlankOrNull(url) || isBlankOrNull(fileName)) {
            throw new InvalidMediaException();
        }
    }

    private boolean isBlankOrNull(final String str) {
        return str == null || str.isBlank();
    }
}
