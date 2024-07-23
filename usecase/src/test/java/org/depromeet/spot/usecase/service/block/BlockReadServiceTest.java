package org.depromeet.spot.usecase.service.block;

import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.service.fake.FakeBlockRepository;
import org.junit.jupiter.api.BeforeEach;

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
}
