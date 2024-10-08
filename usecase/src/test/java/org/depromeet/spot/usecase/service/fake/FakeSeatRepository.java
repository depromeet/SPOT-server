package org.depromeet.spot.usecase.service.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.seat.SeatException.SeatNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;

public class FakeSeatRepository implements SeatRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Seat> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void saveAll(List<Seat> seats) {
        seats.forEach(this::save);
    }

    @Override
    public Seat findById(Long seatId) {
        return getById(seatId).orElseThrow(SeatNotFoundException::new);
    }

    private Optional<Seat> getById(Long id) {
        return data.stream().filter(seat -> seat.getId().equals(id)).findAny();
    }

    @Override
    public Map<BlockRow, List<Seat>> findSeatsGroupByRowInBlock(Block block) {
        return data.stream()
                .filter(seat -> seat.getBlock().getId().equals(block.getId()))
                .collect(Collectors.groupingBy(Seat::getRow));
    }

    @Override
    public Map<BlockRow, List<Seat>> findSeatsGroupByRowInSection(final Long sectionId) {
        return data.stream()
                .filter(seat -> seat.getSection().getId().equals(sectionId))
                .collect(Collectors.groupingBy(Seat::getRow));
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

    @Override
    public Seat findByBlockIdAndSeatNumber(Long seatId) {
        return getById(seatId).orElseThrow(SeatNotFoundException::new);
    }

    @Override
    public Seat findByBlockIdAndSeatNumber(Long blockId, Integer seatNumber) {
        return getByBlockAndSeatNum(seatNumber, blockId).orElseThrow(SeatNotFoundException::new);
    }

    private Optional<Seat> getByBlockAndSeatNum(Integer seatNumber, Long blockId) {
        return data.stream()
                .filter(seat -> seat.getBlock().getId().equals(blockId))
                .filter(seat -> seat.getSeatNumber().equals(seatNumber))
                .findAny();
    }
}
