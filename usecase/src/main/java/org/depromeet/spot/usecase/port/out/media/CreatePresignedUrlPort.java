package org.depromeet.spot.usecase.port.out.media;

import org.depromeet.spot.domain.media.Media;
import org.depromeet.spot.domain.media.MediaProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface CreatePresignedUrlPort {

    // FIXME: 유저 도메인 생성 후 userId -> Member 등으로 교체
    Media forReview(Long userId, PresignedUrlRequest request);

    Media forStadiumSeat(PresignedUrlRequest request);

    @Getter
    @AllArgsConstructor
    class PresignedUrlRequest {
        private String fileExtension;
        private MediaProperty property;
    }
}
