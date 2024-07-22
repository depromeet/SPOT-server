package org.depromeet.spot.usecase.port.out.seat;

import java.util.List;

import org.depromeet.spot.domain.seat.Seat;

public interface SeatRepository {

    void saveAll(List<Seat> seats);

    Seat findById(Long seatId);

    Seat findByIdWith(Long seatId);
}
