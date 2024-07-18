package org.depromeet.spot.domain.seat;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Seat {

    private final Long id;
    private final Stadium stadium;
    private final Section section;
    private final Block block;
    private final BlockRow row;
    private final Integer seatNumber;
}
