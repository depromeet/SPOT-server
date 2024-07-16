package org.depromeet.spot.domain.review;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Keyword {

    private final Long id;
    private final String content;
}
