package org.depromeet.spot.usecase.port.out.seat;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.seat.Seat;

public interface SeatRepository {

    Map<Block, List<Seat>> findBlockSeatsBy(Long sectionId);
}
