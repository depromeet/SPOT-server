package org.depromeet.spot.domain.review;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewKeyword {
    private final Long id;
    private final Review review;
    private final Keyword keyword;
}
