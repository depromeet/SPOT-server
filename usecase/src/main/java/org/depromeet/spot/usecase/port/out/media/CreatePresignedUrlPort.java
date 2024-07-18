package org.depromeet.spot.usecase.port.out.media;

import org.depromeet.spot.domain.media.MediaProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface CreatePresignedUrlPort {

    String forImage(Long userId, PresignedUrlRequest request);

    @Getter
    @AllArgsConstructor
    class PresignedUrlRequest {
        private String fileExtension;
        private MediaProperty property;
    }
}
