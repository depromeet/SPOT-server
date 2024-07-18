package org.depromeet.spot.jpa.seat.repository;

import java.util.List;

import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJdbcRepository seatJdbcRepository;

    @Override
    public void saveAll(List<Seat> seats) {
        seatJdbcRepository.createSeats(seats);
    }
}
