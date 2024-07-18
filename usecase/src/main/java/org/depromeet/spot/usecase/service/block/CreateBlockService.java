package org.depromeet.spot.usecase.service.block;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.depromeet.spot.common.exception.block.BlockException.InvalidBlockRowException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.block.CreateBlockUsecase;
import org.depromeet.spot.usecase.port.in.block.CreateRowUsecase;
import org.depromeet.spot.usecase.port.in.block.CreateRowUsecase.CreateRowCommand;
import org.depromeet.spot.usecase.port.in.seat.CreateSeatUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateBlockService implements CreateBlockUsecase {

    private final BlockRepository blockRepository;
    private final BlockReadUsecase blockReadUsecase;
    private final CreateRowUsecase createRowUsecase;
    private final StadiumReadUsecase stadiumReadUsecase;
    private final SectionReadUsecase sectionReadUsecase;
    private final CreateSeatUsecase createSeatUsecase;

    @Override
    public void create(final Long stadiumId, final Long sectionId, CreateBlockCommand command) {
        checkValidRow(command.maxRows(), command.rowInfos());
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        sectionReadUsecase.checkIsExistsInStadium(stadiumId, sectionId);
        blockReadUsecase.checkIsDuplicateCode(stadiumId, command.code());
        Block block = create(stadiumId, sectionId, command.code(), command.maxRows());
        createRowUsecase.createAll(block, command.rowInfos());
        createSeatUsecase.createAllInBlock(block.getId());
    }

    public Block create(
            final Long stadiumId, final Long sectionId, final String code, final int maxRows) {
        Block block =
                Block.builder()
                        .sectionId(sectionId)
                        .stadiumId(stadiumId)
                        .code(code)
                        .maxRows(maxRows)
                        .build();
        return blockRepository.save(block);
    }

    public void checkValidRow(int blockMaxRow, List<CreateRowCommand> rowCommands) {
        List<Integer> rowNumbers = rowCommands.stream().map(CreateRowCommand::number).toList();
        checkRowSize(blockMaxRow, rowCommands.size());
        checkValidRowNumber(blockMaxRow, rowNumbers);
        checkDuplicateRowNumber(rowNumbers);
    }

    public void checkRowSize(int blockMaxRow, int rowSize) {
        if (blockMaxRow != rowSize) {
            throw new InvalidBlockRowException();
        }
    }

    public void checkValidRowNumber(int blockMaxRow, List<Integer> rowNumberList) {
        for (Integer rowNumber : rowNumberList) {
            if (rowNumber > blockMaxRow) {
                throw new InvalidBlockRowException();
            }
        }
    }

    public void checkDuplicateRowNumber(List<Integer> rowNumberList) {
        Set<Integer> rowNumberSet = new HashSet<>(rowNumberList);
        if (rowNumberSet.size() < rowNumberList.size()) {
            throw new InvalidBlockRowException();
        }
    }
}
