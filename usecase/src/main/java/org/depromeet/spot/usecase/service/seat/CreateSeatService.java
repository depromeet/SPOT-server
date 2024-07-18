package org.depromeet.spot.usecase.service.seat;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.block.ReadBlockRowUsecase;
import org.depromeet.spot.usecase.port.in.seat.CreateSeatUsecase;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateSeatService implements CreateSeatUsecase {

    private final ReadBlockRowUsecase readBlockRowUsecase;
    private final BlockReadUsecase blockReadUsecase;

    private static final int BLOCK_SEAT_START_NUM = 1;

    /**
     * 특정 블럭의 좌석을 일괄 등록하는 메서드. 블럭 내부 열들의 좌석 번호 최대값을 이용해 좌석 번호를 자동으로 채번해요. ex. 1열의 최대 좌석 번호 = 15면,
     * ex. 1열에 1~15번 좌석을 생성
     */
    @Override
    @Description("좌석 중복 생성을 방지하기 위해, 블럭 좌석을 처음 등록할 때만 사용해요. 이후 좌석 추가 등록이 필요하다면 개별 생성 메서드를 이용해주세요.")
    public void createAllInBlock(final Long blockId) {
        Block block = blockReadUsecase.findById(blockId);
        List<BlockRow> rows = readBlockRowUsecase.findAllByBlock(blockId);
        Stadium stadium = Stadium.builder().id(block.getStadiumId()).build();
        Section section = Section.builder().id(block.getSectionId()).build();
        List<Seat> seats = createAutoIncrementSeats(block, stadium, section, rows);
    }

    public List<Seat> createAutoIncrementSeats(
            Block block, Stadium stadium, Section section, List<BlockRow> rows) {
        List<Seat> seats = new ArrayList<>();
        int blockSeatNum = BLOCK_SEAT_START_NUM;
        for (BlockRow row : rows) {
            do {
                seats.add(
                        Seat.builder()
                                .stadium(stadium)
                                .section(section)
                                .block(block)
                                .row(row)
                                .seatNumber(blockSeatNum)
                                .build());
            } while (blockSeatNum <= row.getMaxSeats());
        }
        return seats;
    }
}
