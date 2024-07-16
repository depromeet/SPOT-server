package org.depromeet.spot.application.common.dto;

import org.depromeet.spot.domain.common.RgbCode;

import lombok.Builder;

@Builder
public record RgbCodeResponse(Integer red, Integer green, Integer blue) {

    public static RgbCodeResponse from(RgbCode rgbCode) {
        return new RgbCodeResponse(rgbCode.getRed(), rgbCode.getGreen(), rgbCode.getBlue());
    }
}
