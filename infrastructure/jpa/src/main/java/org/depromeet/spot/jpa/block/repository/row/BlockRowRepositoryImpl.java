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

    @Override
    public List<BlockRow> createAll(List<BlockRow> rows) {
        List<BlockRowEntity> entities =
                blockRowJpaRepository.saveAll(rows.stream().map(BlockRowEntity::from).toList());
        return entities.stream().map(BlockRowEntity::toDomain).toList();
    }

    @Override
    public List<BlockRow> findAllByBlock(Long blockId) {
        List<BlockRowEntity> entities =
                blockRowJpaRepository.findAllByBlockIdOrderByNumberAsc(blockId);
        return entities.stream().map(BlockRowEntity::toDomain).toList();
    }
}
