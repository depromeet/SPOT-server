package org.depromeet.spot.domain.block;

import lombok.Getter;

@Getter
public class BlockTopKeyword {
    private final Long id;
    private final Long blockId;
    private final Long keywordId;
    private final Long count;

    public BlockTopKeyword(Long id, Long blockId, Long keywordId, Long count) {
        this.id = id;
        this.blockId = blockId;
        this.keywordId = keywordId;
        this.count = count;
    }
}
