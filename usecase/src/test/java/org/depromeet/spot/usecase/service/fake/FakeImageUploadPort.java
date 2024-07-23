package org.depromeet.spot.usecase.service.fake;

import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.usecase.port.out.media.ImageUploadPort;
import org.springframework.web.multipart.MultipartFile;

public class FakeImageUploadPort implements ImageUploadPort {

    @Override
    public String upload(String target, MultipartFile file, MediaProperty property) {
        return target + property.name();
    }
}
