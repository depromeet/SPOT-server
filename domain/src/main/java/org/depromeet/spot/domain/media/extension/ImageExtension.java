package org.depromeet.spot.domain.media.extension;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;

import lombok.Getter;

@Getter
public enum ImageExtension {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    HEIC("heic"),
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
            throw new InvalidExtensionException(reqExtension);
        }
        return extension;
    }
}
