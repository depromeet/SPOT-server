package org.depromeet.spot.usecase.port.in.seat;

import org.depromeet.spot.domain.seat.Seat;

public interface ReadSeatUsecase {

    Seat findById(Long seatId);
}
