package org.depromeet.spot.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SnsProvider {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");

    private final String value;
}
