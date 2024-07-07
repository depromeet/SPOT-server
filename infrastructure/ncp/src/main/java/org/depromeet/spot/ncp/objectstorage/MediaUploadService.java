package org.depromeet.spot.ncp.objectstorage;

import org.depromeet.spot.domain.media.Media;
import org.depromeet.spot.ncp.property.ObjectStorageProperties;
import org.depromeet.spot.usecase.port.out.media.MediaUploadPort;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaUploadService implements MediaUploadPort {

    private final AmazonS3 amazonS3;
    private final ObjectStorageProperties objectStorageProperties;

    @Override
    public Media forReview() {
        return null;
    }

    @Override
    public Media forStadium() {
        return null;
    }
}
