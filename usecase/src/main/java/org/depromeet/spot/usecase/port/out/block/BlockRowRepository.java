package org.depromeet.spot.usecase.port.out.block;

import java.util.List;

import org.depromeet.spot.domain.block.BlockRow;

public interface BlockRowRepository {

    List<BlockRow> createAll(List<BlockRow> rows);
}
