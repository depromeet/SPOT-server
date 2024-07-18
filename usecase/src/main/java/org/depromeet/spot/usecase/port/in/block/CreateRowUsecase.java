package org.depromeet.spot.usecase.port.in.block;

import java.util.List;

import org.depromeet.spot.domain.block.Block;

import lombok.Builder;

public interface CreateRowUsecase {

    void createAll(Block block, List<CreateRowCommand> commands);

    @Builder
    record CreateRowCommand(int number, int maxSeatNum) {}
}
