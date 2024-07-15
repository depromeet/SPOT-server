package org.depromeet.spot.application.common.dto;

import org.depromeet.spot.domain.common.RgbCode;

public record RgbCodeRequest(Integer red, Integer green, Integer blue) {
    public RgbCode toDomain() {
        return RgbCode.builder().red(red).green(green).blue(blue).build();
    }
}
