package org.depromeet.spot.jpa.block.repository.row;

import java.util.List;

import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.jpa.block.entity.BlockRowEntity;
import org.depromeet.spot.usecase.port.out.block.BlockRowRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockRowRepositoryImpl implements BlockRowRepository {

    private final BlockRowJpaRepository blockRowJpaRepository;
    private final BlockRowJdbcRepository blockRowJdbcRepository;

    @Override
    public void createAll(List<BlockRow> rows) {
        blockRowJdbcRepository.createRows(rows);
    }

    @Override
    public List<BlockRow> findAllByBlock(Long blockId) {
        List<BlockRowEntity> entities =
                blockRowJpaRepository.findAllByBlockIdOrderByNumberAsc(blockId);
        return entities.stream().map(BlockRowEntity::toDomain).toList();
    }
}