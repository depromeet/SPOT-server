package org.depromeet.spot.usecase.port.in.seat;

import java.util.List;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;

public interface CreateSeatUsecase {

    void createAllInBlock(Block block, List<BlockRow> rows);
}
