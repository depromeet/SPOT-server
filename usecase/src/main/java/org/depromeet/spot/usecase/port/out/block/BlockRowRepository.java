package org.depromeet.spot.usecase.port.out.block;

import java.util.List;

import org.depromeet.spot.domain.block.BlockRow;

public interface BlockRowRepository {

    void createAll(List<BlockRow> rows);

    List<BlockRow> findAllByBlock(Long blockId);

    BlockRow findBy(long stadiumId, String blockCode, int rowNumber);
}
