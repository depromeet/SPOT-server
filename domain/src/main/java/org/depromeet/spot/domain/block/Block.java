package org.depromeet.spot.domain.block;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Block {

    private final Long id;
    private final Long stadiumId;
    private final Long sectionId;
    private final String code;
    private final Integer maxRows;

    public static final int BLOCK_SEAT_START_NUM = 1;

    public Block(Long id, Long stadiumId, Long sectionId, String code, Integer maxRows) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.sectionId = sectionId;
        this.code = code;
        this.maxRows = maxRows;
    }
}
