package org.depromeet.spot.usecase.service.seat;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.seat.ReadSeatUsecase;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadSeatService implements ReadSeatUsecase {

    private final SeatRepository seatRepository;

    @Override
    public Seat findById(Long seatId) {
        return null;
    }

    @Override
    public Map<BlockRow, List<Seat>> findSeatsGroupByRowInBlock(Block block) {
        return seatRepository.findSeatsGroupByRowInBlock(block);
    }

    @Override
    public Map<BlockRow, List<Seat>> findSeatsGroupByRowInSection(final Long sectionId) {
        return seatRepository.findSeatsGroupByRowInSection(sectionId);
    }
}
