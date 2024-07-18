package org.depromeet.spot.usecase.port.in.block;

import lombok.Builder;

public interface CreateRowUsecase {

    @Builder
    record CreateRowCommand(int number, int minSeatNum, int maxSeatNum) {}
}
