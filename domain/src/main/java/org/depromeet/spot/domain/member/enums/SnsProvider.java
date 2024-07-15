package org.depromeet.spot.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SnsProvider {
    KAKAO("KAKAO");

    private final String value;
}
