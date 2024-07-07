package org.depromeet.spot.ncp.objectstorage;

import org.depromeet.spot.domain.media.Media;
import org.depromeet.spot.usecase.port.out.media.MediaUploadPort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaUploadService implements MediaUploadPort {

    @Override
    public Media upload() {
        return null;
    }
}
