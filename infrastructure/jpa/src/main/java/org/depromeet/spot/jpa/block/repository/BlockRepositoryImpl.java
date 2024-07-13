package org.depromeet.spot.jpa.block.repository;

import java.util.List;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockRepositoryImpl implements BlockRepository {

    private final BlockJpaRepository blockJpaRepository;

    @Override
    public List<Block> findAllBySection(final Long sectionId) {
        List<BlockEntity> entities = blockJpaRepository.findAllBySectionId(sectionId);
        return entities.stream().map(BlockEntity::toDomain).toList();
    }
}
