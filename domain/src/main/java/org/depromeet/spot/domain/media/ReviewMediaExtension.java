package org.depromeet.spot.domain.media;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum ReviewMediaExtension {
    JPG("jpeg"),
    JPEG("jpeg"),
    PNG("png"),
    MP4("mp4"),
    MPEG("mpeg"),
    AVI("avi"),
    ;

    private final String value;

    private static final Map<String, ReviewMediaExtension> cachedReviewMedia =
            Arrays.stream(ReviewMediaExtension.values())
                    .collect(
                            Collectors.toMap(extension -> extension.value, extension -> extension));

    ReviewMediaExtension(final String value) {
        this.value = value;
    }

    public static boolean isValidReviewMedia(final String reqExtension) {
        return cachedReviewMedia.containsKey(reqExtension);
    }

    public static ReviewMediaExtension from(final String reqExtension) {
        ReviewMediaExtension extension = cachedReviewMedia.get(reqExtension);
        if (extension == null) {
            throw new IllegalStateException("지원하지 않는 리뷰 첨부파일 확장자입니다.");
        }
        return extension;
    }
}
