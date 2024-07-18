package org.depromeet.spot.usecase.service.block;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.depromeet.spot.common.exception.block.BlockException.InvalidBlockRowException;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.block.CreateRowUsecase;
import org.depromeet.spot.usecase.port.in.seat.CreateSeatUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.service.fake.FakeBlockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateBlockServiceTest {

    private BlockReadUsecase blockReadUsecase;
    private CreateRowUsecase createRowUsecase;
    private StadiumReadUsecase stadiumReadUsecase;
    private SectionReadUsecase sectionReadUsecase;
    private CreateSeatUsecase createSeatUsecase;
    private CreateBlockService createBlockService;

    @BeforeEach
    void init() {
        FakeBlockRepository fakeBlockRepository = new FakeBlockRepository();
        this.createBlockService =
                CreateBlockService.builder()
                        .blockRepository(fakeBlockRepository)
                        .blockReadUsecase(blockReadUsecase)
                        .createRowUsecase(createRowUsecase)
                        .stadiumReadUsecase(stadiumReadUsecase)
                        .sectionReadUsecase(sectionReadUsecase)
                        .createSeatUsecase(createSeatUsecase)
                        .build();
    }

    @Test
    public void 열_생성_요청에_중복이_없고_블록_최대_열_번호를_만족하면_에러를_반환하지_않는다() {
        // given
        final int blockMaxRow = 5;
        List<Integer> rowNumbers = List.of(1, 2, 3, 4, 5);

        // when
        // then
        assertDoesNotThrow(() -> createBlockService.checkValidRow(blockMaxRow, rowNumbers));
    }

    @Test
    public void 블럭_최대_열_수와_요청_열_수가_다르면_에러를_반환한다() {
        // given
        final int blockMaxRow = 3;
        List<Integer> rowNumbers = List.of(1, 2, 3, 4);

        // when
        // then
        assertThatThrownBy(() -> createBlockService.checkRowSize(blockMaxRow, rowNumbers))
                .isInstanceOf(InvalidBlockRowException.class);
    }

    @Test
    public void 열_생성_요청에_중복_열_번호가_있다면_에러를_반환한다() {
        // given
        List<Integer> rowNumbers = List.of(1, 2, 2);

        // when
        // then
        assertThatThrownBy(() -> createBlockService.checkDuplicateRowNumber(rowNumbers))
                .isInstanceOf(InvalidBlockRowException.class);
    }

    @Test
    public void 열_생성_요청에_블럭_최대_열_번호_보다_큰_번호가_있다면_에러를_반환한다() {
        // given
        final int blockMaxRow = 3;
        List<Integer> rowNumbers = List.of(1, 5, 10);

        // when
        // then
        assertThatThrownBy(() -> createBlockService.checkValidRowNumber(blockMaxRow, rowNumbers))
                .isInstanceOf(InvalidBlockRowException.class);
    }
}
