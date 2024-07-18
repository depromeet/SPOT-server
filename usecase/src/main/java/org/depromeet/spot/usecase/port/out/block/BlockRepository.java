package org.depromeet.spot.usecase.port.out.block;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;

public interface BlockRepository {

    List<Block> findAllBySection(Long sectionId);

    Map<Block, List<BlockRow>> findRowInfosBy(Long sectionId);

    List<BlockRow> findAllByBlock(Long blockId);

    List<BlockRow> findAllByStadiumAndBlock(Long stadiumId, String blockCode);

    boolean existsById(Long blockId);

    boolean existsByStadiumAndCode(Long stadiumId, String code);

    Block findById(Long blockId);

    Block findByStadiumAndCode(Long stadiumId, String code);

    Block save(Block block);
}
