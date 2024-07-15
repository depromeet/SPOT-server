package org.depromeet.spot.domain.block;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BlockTopKeyword {
    private final Long id;
    private final Long blockId;
    private final Long keywordId;
    private final Long count;
}
