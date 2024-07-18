package org.depromeet.spot.usecase.service.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.block.BlockException.BlockNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;

public class FakeBlockRepository implements BlockRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Block> blockData = Collections.synchronizedList(new ArrayList<>());
    private final List<BlockRow> rowData = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<Block> findAllBySection(Long sectionId) {
        return blockData.stream().filter(b -> b.getSectionId().equals(sectionId)).toList();
    }

    @Override
    public Map<Block, List<BlockRow>> findRowInfosBy(Long sectionId) {
        return rowData.stream().collect(Collectors.groupingBy(BlockRow::getBlock));
    }

    @Override
    public List<BlockRow> findAllByBlock(Long blockId) {
        return rowData.stream().filter(row -> row.getBlock().getId().equals(blockId)).toList();
    }

    @Override
    public List<BlockRow> findAllByBlock(String blockCode) {
        return rowData.stream().filter(row -> row.getBlock().getCode().equals(blockCode)).toList();
    }

    @Override
    public boolean existsById(Long blockId) {
        return blockData.stream().anyMatch(block -> block.getId().equals(blockId));
    }

    @Override
    public Block findById(Long blockId) {
        return getById(blockId).orElseThrow(BlockNotFoundException::new);
    }

    @Override
    public Block findByStadiumAndCode(Long stadiumId, String code) {
        return getByStadiumAndCode(stadiumId, code).orElseThrow(BlockNotFoundException::new);
    }

    private Optional<Block> getById(Long id) {
        return blockData.stream().filter(block -> block.getId().equals(id)).findAny();
    }

    private Optional<Block> getByStadiumAndCode(Long stadiumId, String code) {
        return blockData.stream()
                .filter(block -> block.getCode().equals(code))
                .filter(block -> block.getStadiumId().equals(stadiumId))
                .findAny();
    }
}
