package org.depromeet.spot.application.media.dto.request;

import org.depromeet.spot.domain.media.MediaProperty;

public record CreatePresignedUrlRequest(String fileExtension, MediaProperty property) {}
