package org.depromeet.spot.domain.common;

import java.util.regex.Pattern;

import org.depromeet.spot.common.exception.util.UtilException.InvalidHexCodeException;

import lombok.Getter;

@Getter
public class HexCode {

    private static final Pattern HEX_PATTERN = Pattern.compile("^#[0-9A-Fa-f]{6}$");
    private final String value;

    public HexCode(final String input) {
        if (!isValid(input)) {
            throw new InvalidHexCodeException();
        }
        this.value = input;
    }

    private boolean isValid(final String input) {
        return input != null && HEX_PATTERN.matcher(input).matches();
    }
}
