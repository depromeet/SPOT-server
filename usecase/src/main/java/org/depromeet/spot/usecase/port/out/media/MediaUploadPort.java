package org.depromeet.spot.usecase.port.out.media;

import org.depromeet.spot.domain.media.Media;

public interface MediaUploadPort {

    Media forReview();

    Media forStadium();
}
