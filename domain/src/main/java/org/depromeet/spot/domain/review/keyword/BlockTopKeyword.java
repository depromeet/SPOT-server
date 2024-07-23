package org.depromeet.spot.domain.review.keyword;

import org.depromeet.spot.domain.block.Block;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockTopKeyword {
    private final Long id;
    private final Block block;
    private final Keyword keyword;
    private final Long count;
}
