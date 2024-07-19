package org.depromeet.spot.domain.block;

import org.depromeet.spot.domain.review.ReviewKeyword;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockTopKeyword {
    private final Long id;
    private final Block block;
    private final ReviewKeyword keyword;
    private final Long count;
}
