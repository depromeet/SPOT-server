package org.depromeet.spot.domain.seat;

import lombok.Getter;

@Getter
public class Seat {

    private final Long id;
    private final Long stadiumId;
    private final Long sectionId;
    private final Long blockId;
    private final Long rowId;
    private final Integer seatNumber;

    public Seat(
            Long id, Long stadiumId, Long sectionId, Long blockId, Long rowId, Integer seatNumber) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.sectionId = sectionId;
        this.blockId = blockId;
        this.rowId = rowId;
        this.seatNumber = seatNumber;
    }
}
