package org.depromeet.spot.usecase.port.out.block;

import java.util.List;

import org.depromeet.spot.domain.block.Block;

public interface BlockRepository {

    List<Block> findAllBySection(Long sectionId);
}
