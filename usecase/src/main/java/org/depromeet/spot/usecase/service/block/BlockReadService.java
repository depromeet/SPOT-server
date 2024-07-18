package org.depromeet.spot.usecase.service.block;

import static org.depromeet.spot.domain.block.Block.BLOCK_SEAT_START_NUM;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.depromeet.spot.common.exception.block.BlockException.BlockNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class BlockReadService implements BlockReadUsecase {

    private final BlockRepository blockRepository;
    private final StadiumReadUsecase stadiumReadUsecase;
    private final SectionReadUsecase sectionReadUsecase;

    @Override
    public List<BlockCodeInfo> findCodeInfosByStadium(final Long stadiumId, final Long sectionId) {
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        sectionReadUsecase.checkIsExistsInStadium(stadiumId, sectionId);
        List<Block> blocks = blockRepository.findAllBySection(sectionId);
        return blocks.stream().map(b -> new BlockCodeInfo(b.getId(), b.getCode())).toList();
    }

    @Override
    public List<BlockInfo> findAllBlockInfoBy(final Long stadiumId, final Long sectionId) {
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        sectionReadUsecase.checkIsExistsInStadium(stadiumId, sectionId);

        Map<Block, List<BlockRow>> blockRows = blockRepository.findRowInfosBy(sectionId);
        List<BlockInfo> result =
                blockRows.entrySet().stream()
                        .map(
                                entry -> {
                                    Block block = entry.getKey();
                                    List<RowInfo> rowInfos = getBlockRowInfos(entry.getValue());
                                    return new BlockInfo(block.getId(), block.getCode(), rowInfos);
                                })
                        .sorted(Comparator.comparing(BlockInfo::getId))
                        .toList();

        result.forEach(block -> block.getRowInfo().sort(Comparator.comparing(RowInfo::getNumber)));

        return result;
    }

    @Override
    public BlockInfo findBlockInfoBy(final Long stadiumId, final String blockCode) {
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        Block block = findByStadiumAndCode(stadiumId, blockCode);
        List<BlockRow> infos = blockRepository.findAllByBlock(blockCode);
        List<RowInfo> rowInfos = getBlockRowInfos(infos);
        return new BlockInfo(block.getId(), block.getCode(), rowInfos);
    }

    @Override
    public Block findById(final Long blockId) {
        return blockRepository.findById(blockId);
    }

    @Override
    public Block findByStadiumAndCode(final Long stadiumId, final String code) {
        return blockRepository.findByStadiumAndCode(code);
    }

    @Override
    public boolean existsById(final Long blockId) {
        return blockRepository.existsById(blockId);
    }

    @Override
    public void checkExistsById(final Long blockId) {
        if (!existsById(blockId)) {
            throw new BlockNotFoundException("id : " + blockId);
        }
    }

    public List<RowInfo> getBlockRowInfos(List<BlockRow> rows) {
        List<RowInfo> rowInfos = new ArrayList<>();
        int lastSeatNum = BLOCK_SEAT_START_NUM;

        for (BlockRow row : rows) {
            int minSeatNum = lastSeatNum;
            int maxSeatNum = row.getMaxSeats();
            rowInfos.add(
                    RowInfo.builder()
                            .id(row.getId())
                            .number(row.getNumber())
                            .maxSeatNum(maxSeatNum)
                            .minSeatNum(minSeatNum)
                            .build());
            lastSeatNum = ++maxSeatNum;
        }
        return rowInfos;
    }
}
