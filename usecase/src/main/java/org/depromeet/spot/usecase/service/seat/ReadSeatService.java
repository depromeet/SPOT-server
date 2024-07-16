package org.depromeet.spot.usecase.service.seat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.section.SectionException.SectionNotBelongStadiumException;
import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.seat.ReadSeatUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadSeatService implements ReadSeatUsecase {

    private final SeatRepository seatRepository;
    private final StadiumReadUsecase stadiumReadUsecase;
    private final SectionReadUsecase sectionReadUsecase;

    @Override
    public List<BlockInfo> findAllBlockInfoBy(final Long stadiumId, final Long sectionId) {
        List<BlockInfo> result = new ArrayList<>();
        if (!stadiumReadUsecase.existsById(sectionId)) {
            throw new StadiumNotFoundException();
        }

        if (!sectionReadUsecase.existsInStadium(stadiumId, sectionId)) {
            throw new SectionNotBelongStadiumException();
        }

        Map<Block, List<Seat>> seatPerBlock = seatRepository.findBlockSeatsBy(sectionId);
        for (Entry<Block, List<Seat>> entry : seatPerBlock.entrySet()) {
            Block block = entry.getKey();
            List<RowInfo> rowInfos = getBlockRowInfos(entry.getValue());
            result.add(new BlockInfo(block.getId(), block.getCode(), rowInfos));
        }

        return result;
    }

    public List<RowInfo> getBlockRowInfos(List<Seat> seats) {
        List<RowInfo> rowInfos = new ArrayList<>();
        int lastSeatNum = 1;

        Map<BlockRow, List<Seat>> seatsByRow =
                seats.stream().collect(Collectors.groupingBy(Seat::getRow));
        for (Entry<BlockRow, List<Seat>> seatEntry : seatsByRow.entrySet()) {
            BlockRow row = seatEntry.getKey();
            int minSeatNum = lastSeatNum;
            int maxSeatNum = seatEntry.getKey().getMaxSeats();
            lastSeatNum = maxSeatNum;
            rowInfos.add(
                    RowInfo.builder()
                            .id(row.getId())
                            .number(row.getNumber())
                            .maxSeatNum(maxSeatNum)
                            .minSeatNum(minSeatNum)
                            .build());
        }
        return rowInfos;
    }
}
