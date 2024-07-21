package org.depromeet.spot.usecase.service.block;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.depromeet.spot.common.exception.block.BlockException.BlockCodeDuplicateException;
import org.depromeet.spot.common.exception.block.BlockException.BlockNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
import org.depromeet.spot.usecase.port.in.seat.ReadSeatUsecase;
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
    private final ReadSeatUsecase readSeatUsecase;

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

        Map<BlockRow, List<Seat>> rowGroup =
                readSeatUsecase.findSeatsGroupByRowInSection(sectionId);
        List<BlockInfo> blockInfos = getBlockInfos(rowGroup);
        blockInfos.sort(Comparator.comparing(BlockInfo::getCode));
        blockInfos.forEach(
                block -> block.getRowInfo().sort(Comparator.comparing(RowInfo::getNumber)));

        return blockInfos;
    }

    @Override
    public BlockInfo findBlockInfoBy(final Long stadiumId, final String blockCode) {
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        Block block = findByStadiumAndCode(stadiumId, blockCode);
        Map<BlockRow, List<Seat>> seatsGroup = readSeatUsecase.findSeatsGroupByRowInBlock(block);
        List<RowInfo> rowInfos = getBlockRowInfos(seatsGroup);
        return new BlockInfo(block.getId(), block.getCode(), rowInfos);
    }

    @Override
    public Block findById(final Long blockId) {
        return blockRepository.findById(blockId);
    }

    @Override
    public Block findByStadiumAndCode(final Long stadiumId, final String code) {
        return blockRepository.findByStadiumAndCode(stadiumId, code);
    }

    @Override
    public void checkIsDuplicateCode(Long stadiumId, String code) {
        if (blockRepository.existsByStadiumAndCode(stadiumId, code)) {
            throw new BlockCodeDuplicateException("code : " + code);
        }
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

    public List<RowInfo> getBlockRowInfos(Map<BlockRow, List<Seat>> seatsGroup) {
        List<RowInfo> rowInfos = new ArrayList<>();

        for (Entry<BlockRow, List<Seat>> entry : seatsGroup.entrySet()) {
            BlockRow row = entry.getKey();
            List<Seat> seats = entry.getValue();
            List<Integer> seatNumList = seats.stream().map(Seat::getSeatNumber).toList();

            rowInfos.add(
                    RowInfo.builder()
                            .id(row.getId())
                            .number(row.getNumber())
                            .seatNumList(seatNumList)
                            .build());
        }

        return rowInfos;
    }

    public List<BlockInfo> getBlockInfos(Map<BlockRow, List<Seat>> seatsGroup) {
        List<BlockInfo> blockInfos = new ArrayList<>();

        for (Entry<BlockRow, List<Seat>> entry : seatsGroup.entrySet()) {
            BlockRow row = entry.getKey();
            Block block = row.getBlock();
            List<RowInfo> rowInfo = getBlockRowInfos(Map.of(row, entry.getValue()));

            blockInfos.add(new BlockInfo(block.getId(), block.getCode(), rowInfo));
        }

        return blockInfos;
    }
}
