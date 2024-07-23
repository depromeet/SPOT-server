package org.depromeet.spot.usecase.port.in.seat;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;

public interface ReadSeatUsecase {

    Seat findById(Long seatId);

    Map<BlockRow, List<Seat>> findSeatsGroupByRowInBlock(Block block);

    Map<BlockRow, List<Seat>> findSeatsGroupByRowInSection(Long sectionId);
}
