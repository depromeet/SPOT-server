package org.depromeet.spot.ncp.objectstorage;

import org.depromeet.spot.domain.media.extension.ImageExtension;
import org.depromeet.spot.usecase.port.in.util.TimeUsecase;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class FileNameGenerator {

    private final TimeUsecase timeUsecase;

    public String createFileName(
            final Long memberId, final ImageExtension fileExtension, final String folderName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(folderName)
                .append("/")
                .append("user_")
                .append(memberId)
                .append("_")
                .append(timeUsecase.getNow())
                .append(".")
                .append(fileExtension.getValue());
        return stringBuilder.toString();
    }
}
