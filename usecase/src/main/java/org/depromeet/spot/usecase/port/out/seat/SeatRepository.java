package org.depromeet.spot.usecase.port.out.seat;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;

public interface SeatRepository {

    void saveAll(List<Seat> seats);

    Seat findById(Long seatId);

    Seat findByIdWith(Long seatId);

    Map<BlockRow, List<Seat>> findSeatsGroupByRowInBlock(Block block);

    Map<BlockRow, List<Seat>> findSeatsGroupByRowInSection(Long sectionId);
}
