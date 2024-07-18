package org.depromeet.spot.usecase.service.block;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.block.CreateBlockUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateBlockService implements CreateBlockUsecase {

    private final BlockRepository blockRepository;
    private final BlockReadUsecase blockReadUsecase;
    private final StadiumReadUsecase stadiumReadUsecase;
    private final SectionReadUsecase sectionReadUsecase;

    @Override
    public void create(final Long stadiumId, final Long sectionId, CreateBlockCommand command) {
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        sectionReadUsecase.checkIsExistsInStadium(stadiumId, sectionId);
        blockReadUsecase.checkIsDuplicateCode(stadiumId, command.code());
        Block block =
                Block.builder()
                        .sectionId(sectionId)
                        .stadiumId(stadiumId)
                        .code(command.code())
                        .maxRows(command.maxRows())
                        .build();
    }
}
