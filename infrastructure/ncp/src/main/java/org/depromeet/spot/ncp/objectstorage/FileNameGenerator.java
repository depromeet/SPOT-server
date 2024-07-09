package org.depromeet.spot.ncp.objectstorage;

import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.media.extension.ImageExtension;
import org.depromeet.spot.domain.media.extension.StadiumSeatMediaExtension;
import org.depromeet.spot.usecase.port.in.util.TimeUsecase;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileNameGenerator {

    private final TimeUsecase timeUsecase;

    public String createReviewFileName(final Long userId, final ImageExtension fileExtension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(MediaProperty.REVIEW)
                .append("/user/")
                .append(userId)
                .append("/")
                .append(timeUsecase.getNow())
                .append("/")
                .append(fileExtension.getValue());
        return stringBuilder.toString();
    }

    public String createStadiumFileName(final StadiumSeatMediaExtension fileExtension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(MediaProperty.STADIUM)
                .append("/")
                .append(timeUsecase.getNow())
                .append("/")
                .append(fileExtension.getValue());
        return stringBuilder.toString();
    }
}
