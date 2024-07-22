package org.depromeet.spot.application.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtTokenEnums {
    BEARER("Bearer");

    private final String value;
}
