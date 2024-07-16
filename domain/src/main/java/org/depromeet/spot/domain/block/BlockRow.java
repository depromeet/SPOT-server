package org.depromeet.spot.domain.block;

import lombok.Getter;

@Getter
public class BlockRow {

    private final Long id;
    private final Long blockId;
    private final Long number;
    private final Long maxSeats;

    public BlockRow(Long id, Long blockId, Long number, Long max_seats) {
        this.id = id;
        this.blockId = blockId;
        this.number = number;
        this.maxSeats = max_seats;
    }
}
