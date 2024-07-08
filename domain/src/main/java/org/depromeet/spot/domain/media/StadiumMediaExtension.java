package org.depromeet.spot.domain.media;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum StadiumMediaExtension {
    SVG("svg"),
    ;

    private final String value;

    private static final Map<String, StadiumMediaExtension> cachedStadiumMedia =
            Arrays.stream(StadiumMediaExtension.values())
                    .collect(
                            Collectors.toMap(extension -> extension.value, extension -> extension));

    StadiumMediaExtension(final String value) {
        this.value = value;
    }

    public static boolean isValidVideoExtension(final String reqExtension) {
        return cachedStadiumMedia.containsKey(reqExtension);
    }

    public static StadiumMediaExtension from(final String reqExtension) {
        StadiumMediaExtension extension = cachedStadiumMedia.get(reqExtension);
        if (extension == null) {
            throw new IllegalStateException("지원하지 않는 경기장 첨부파일 확장자입니다.");
        }
        return extension;
    }
}
