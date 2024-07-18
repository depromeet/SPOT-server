package org.depromeet.spot.usecase.service.seat;

import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.seat.ReadSeatUsecase;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadSeatService implements ReadSeatUsecase {

    private final SeatRepository seatRepository;

    @Override
    public Seat findById(final Long seatId) {
        return seatRepository.findById(seatId);
    }
}
