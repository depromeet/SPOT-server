package org.depromeet.spot.domain.media.extension;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum ImageExtension {
    JPG("jpeg"),
    JPEG("jpeg"),
    PNG("png"),
    ;

    private final String value;

    private static final Map<String, ImageExtension> cachedImageExtension =
            Arrays.stream(ImageExtension.values())
                    .collect(
                            Collectors.toMap(extension -> extension.value, extension -> extension));

    ImageExtension(final String value) {
        this.value = value;
    }

    public static boolean isValid(final String reqExtension) {
        return cachedImageExtension.containsKey(reqExtension);
    }

    public static ImageExtension from(final String reqExtension) {
        ImageExtension extension = cachedImageExtension.get(reqExtension);
        if (extension == null) {
            throw new IllegalStateException("지원하지 않는 리뷰 첨부파일 확장자입니다.");
        }
        return extension;
    }
}
