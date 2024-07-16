package org.depromeet.spot.domain.common;

import org.depromeet.spot.common.exception.util.UtilException.InvalidRgbCodeException;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RgbCode {

    private final Integer red;
    private final Integer green;
    private final Integer blue;

    public RgbCode(Integer red, Integer green, Integer blue) {
        isValidRgb(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private static void isValidRgb(Integer red, Integer green, Integer blue) {
        if (red == null || green == null || blue == null) {
            throw new InvalidRgbCodeException();
        }
    }
}