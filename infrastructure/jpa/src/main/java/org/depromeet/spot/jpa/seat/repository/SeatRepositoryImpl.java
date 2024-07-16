package org.depromeet.spot.jpa.seat.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.jpa.seat.entity.SeatEntity;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatCustomRepository seatCustomRepository;

    @Override
    public Map<Block, List<Seat>> findBlockSeatsBy(final Long sectionId) {
        Map<BlockEntity, List<SeatEntity>> entityMap =
                seatCustomRepository.findBlockSeatsBy(sectionId);
        return entityMap.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey().toDomain(),
                                entry ->
                                        entry.getValue().stream()
                                                .map(SeatEntity::toDomain)
                                                .toList()));
    }
}
