package org.depromeet.spot.usecase.port.in.block;

import java.util.List;

import org.depromeet.spot.domain.block.BlockRow;

public interface ReadBlockRowUsecase {

    List<BlockRow> findAllByBlock(Long blockId);
}
