package org.depromeet.spot.usecase.service.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;

public class FakeSeatRepository implements SeatRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Seat> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void saveAll(List<Seat> seats) {
        seats.forEach(this::save);
    }

    private Seat save(Seat seat) {
        if (seat.getId() == null || seat.getId() == 0) {
            Seat newSeat =
                    Seat.builder()
                            .id(autoGeneratedId.incrementAndGet())
                            .stadium(seat.getStadium())
                            .section(seat.getSection())
                            .block(seat.getBlock())
                            .row(seat.getRow())
                            .seatNumber(seat.getSeatNumber())
                            .build();
            data.add(newSeat);
            return newSeat;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), seat.getId()));
            data.add(seat);
            return seat;
        }
    }
}