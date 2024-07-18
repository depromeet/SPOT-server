package org.depromeet.spot.usecase.service.seat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.block.ReadBlockRowUsecase;
import org.depromeet.spot.usecase.service.fake.FakeSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateSeatServiceTest {

    private CreateSeatService createSeatService;
    private ReadBlockRowUsecase readBlockRowUsecase;
    private BlockReadUsecase blockReadUsecase;

    @BeforeEach
    void init() {
        FakeSeatRepository fakeSeatRepository = new FakeSeatRepository();
        this.createSeatService =
                CreateSeatService.builder()
                        .seatRepository(fakeSeatRepository)
                        .readBlockRowUsecase(readBlockRowUsecase)
                        .blockReadUsecase(blockReadUsecase)
                        .build();
    }

    @Test
    void 블록_열_정보가_주어졌을_때_자동으로_좌석을_채번할_수_있다() {
        // given
        Block block = Block.builder().id(1L).build();
        Stadium stadium = Stadium.builder().id(1L).build();
        Section section = Section.builder().id(1L).build();
        List<BlockRow> rows =
                new ArrayList<>(
                        List.of(
                                BlockRow.builder().number(1).maxSeats(10).build(),
                                BlockRow.builder().number(2).maxSeats(14).build(),
                                BlockRow.builder().number(3).maxSeats(18).build()));

        // when
        List<Seat> seats =
                createSeatService.createAutoIncrementSeats(block, stadium, section, rows);

        // then
        assertEquals(18, seats.size());
        IntStream.rangeClosed(1, 18)
                .forEach(i -> assertEquals(i, seats.get(i - 1).getSeatNumber()));
    }
}
