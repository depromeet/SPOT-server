package org.depromeet.spot.usecase.service.block;

import static org.depromeet.spot.domain.block.Block.BLOCK_SEAT_START_NUM;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.depromeet.spot.common.exception.section.SectionException.SectionNotBelongStadiumException;
import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockReadService implements BlockReadUsecase {

    private final BlockRepository blockRepository;
    private final StadiumReadUsecase stadiumReadUsecase;
    private final SectionReadUsecase sectionReadUsecase;

    @Override
    public List<BlockCodeInfo> findCodeInfosByStadium(final Long stadiumId, final Long sectionId) {
        if (!stadiumReadUsecase.existsById(stadiumId)) {
            throw new StadiumNotFoundException();
        }
        if (!sectionReadUsecase.existsInStadium(stadiumId, sectionId)) {
            throw new SectionNotBelongStadiumException();
        }
        List<Block> blocks = blockRepository.findAllBySection(sectionId);
        return blocks.stream().map(b -> new BlockCodeInfo(b.getId(), b.getCode())).toList();
    }

    @Override
    public List<BlockInfo> findAllBlockInfoBy(final Long stadiumId, final Long sectionId) {
        List<BlockInfo> result = new ArrayList<>();
        if (!stadiumReadUsecase.existsById(sectionId)) {
            throw new StadiumNotFoundException();
        }

        if (!sectionReadUsecase.existsInStadium(stadiumId, sectionId)) {
            throw new SectionNotBelongStadiumException();
        }

        Map<Block, List<BlockRow>> blockRows = blockRepository.findRowInfosBy(sectionId);
        for (Entry<Block, List<BlockRow>> entry : blockRows.entrySet()) {
            Block block = entry.getKey();
            List<RowInfo> rowInfos = getBlockRowInfos(entry.getValue());
            result.add(new BlockInfo(block.getId(), block.getCode(), rowInfos));
        }

        result.sort(Comparator.comparing(BlockInfo::getId));
        result.forEach(block -> block.getRowInfo().sort(Comparator.comparing(RowInfo::getNumber)));

        return result;
    }

    public List<RowInfo> getBlockRowInfos(List<BlockRow> seats) {
        List<RowInfo> rowInfos = new ArrayList<>();
        int lastSeatNum = BLOCK_SEAT_START_NUM;

        for (BlockRow row : seats) {
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
