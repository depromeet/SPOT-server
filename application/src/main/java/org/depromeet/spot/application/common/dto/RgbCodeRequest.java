package org.depromeet.spot.application.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.depromeet.spot.domain.common.RgbCode;

import io.swagger.v3.oas.annotations.Parameter;

public record RgbCodeRequest(
        @Parameter(description = "rgb 코드 값:: red")
                @Max(value = 255, message = "rgb 값은 0~255 만 입력 가능합니다.")
                @Min(value = 0, message = "rgb 값은 0~255 만 입력 가능합니다.")
                Integer red,
        @Parameter(description = "rgb 코드 값:: green")
                @Max(value = 255, message = "rgb 값은 0~255 만 입력 가능합니다.")
                @Min(value = 0, message = "rgb 값은 0~255 만 입력 가능합니다.")
                Integer green,
        @Parameter(description = "rgb 코드 값:: blue")
                @Max(value = 255, message = "rgb 값은 0~255 만 입력 가능합니다.")
                @Min(value = 0, message = "rgb 값은 0~255 만 입력 가능합니다.")
                Integer blue) {
    public RgbCode toDomain() {
        return RgbCode.builder().red(red).green(green).blue(blue).build();
    }
}
