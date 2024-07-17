package org.depromeet.spot.usecase.service.block;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase.RowInfo;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.service.fake.FakeBlockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockReadServiceTest {

    private BlockReadService blockReadService;
    private StadiumReadUsecase stadiumReadUsecase;
    private SectionReadUsecase sectionReadUsecase;

    @BeforeEach
    void init() {
        FakeBlockRepository blockRepository = new FakeBlockRepository();
        this.blockReadService =
                BlockReadService.builder()
                        .stadiumReadUsecase(stadiumReadUsecase)
                        .sectionReadUsecase(sectionReadUsecase)
                        .blockRepository(blockRepository)
                        .build();
    }

    @Test
    void 블록별_열에_존재하는_좌석_번호_범위를_생성할_수_있다() {
        // given
        Block block =
                Block.builder().id(1L).stadiumId(1L).sectionId(1L).code("block").maxRows(3).build();
        List<BlockRow> blockRows =
                List.of(
                        BlockRow.builder().id(1L).block(block).number(1).maxSeats(3).build(),
                        BlockRow.builder().id(2L).block(block).number(2).maxSeats(7).build(),
                        BlockRow.builder().id(3L).block(block).number(3).maxSeats(12).build());

        // when
        List<RowInfo> result = blockReadService.getBlockRowInfos(blockRows);

        // then
        Map<Integer, RowInfo> rowInfoMap =
                result.stream().collect(Collectors.toMap(RowInfo::getNumber, Function.identity()));

        assertAll(
                () -> assertRowInfo(rowInfoMap.get(1), 1, 3),
                () -> assertRowInfo(rowInfoMap.get(2), 4, 7),
                () -> assertRowInfo(rowInfoMap.get(3), 8, 12));
    }

    private void assertRowInfo(RowInfo rowInfo, int expectedMinSeatNum, int expectedMaxSeatNum) {
        assertAll(
                () -> assertEquals(expectedMinSeatNum, rowInfo.getMinSeatNum()),
                () -> assertEquals(expectedMaxSeatNum, rowInfo.getMaxSeatNum()));
    }
}
