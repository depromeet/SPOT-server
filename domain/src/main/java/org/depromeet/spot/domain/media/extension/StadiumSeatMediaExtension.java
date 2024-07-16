package org.depromeet.spot.domain.media.extension;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.media.MediaException.InvalidExtensionException;

import lombok.Getter;

@Getter
public enum StadiumSeatMediaExtension {
    SVG("svg"),
    ;

    private final String value;

    private static final Map<String, StadiumSeatMediaExtension> cachedStadiumMedia =
            Arrays.stream(StadiumSeatMediaExtension.values())
                    .collect(
                            Collectors.toMap(extension -> extension.value, extension -> extension));

    StadiumSeatMediaExtension(final String value) {
        this.value = value;
    }

    public static boolean isValid(final String reqExtension) {
        return cachedStadiumMedia.containsKey(reqExtension);
    }

    public static StadiumSeatMediaExtension from(final String reqExtension) {
        StadiumSeatMediaExtension extension = cachedStadiumMedia.get(reqExtension);
        if (extension == null) {
            throw new InvalidExtensionException(reqExtension);
        }
        return extension;
    }
}
