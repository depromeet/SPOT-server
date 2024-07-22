package org.depromeet.spot.jpa.seat.repository;

import java.util.List;

import org.depromeet.spot.common.exception.seat.SeatException.SeatNotFoundException;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.jpa.seat.entity.SeatEntity;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJdbcRepository seatJdbcRepository;
    private final SeatJpaRepository seatJpaRepository;

    @Override
    public void saveAll(List<Seat> seats) {
        seatJdbcRepository.createSeats(seats);
    }

    @Override
    public Seat findById(Long seatId) {
        SeatEntity entity =
                seatJpaRepository.findById(seatId).orElseThrow(SeatNotFoundException::new);
        return entity.toDomain();
    }

    @Override
    public Seat findByIdWith(Long seatId) {
        SeatEntity entity =
                seatJpaRepository.findByIdWith(seatId).orElseThrow(SeatNotFoundException::new);
        return entity.toDomain();
    }
}
