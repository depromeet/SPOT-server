package org.depromeet.spot.usecase.service.review.processor;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;

public interface ReviewDataProcessor {
    Seat getSeat(Long blockId, Integer seatNumber);

    Block getBlock(Long blockId);

    Stadium getStadium(Long stadiumId);

    Section getSection(Long sectionId);

    BlockRow getBlockRow(Long stadiumId, String blockCode, Integer rowNumber);

    Seat getSeatWithDetails(Long blockId, Integer seatNumber);
}
