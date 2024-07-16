package org.depromeet.spot.usecase.port.out.media;

import org.depromeet.spot.domain.media.MediaProperty;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadPort {

    String upload(String target, MultipartFile file, MediaProperty property);
}
