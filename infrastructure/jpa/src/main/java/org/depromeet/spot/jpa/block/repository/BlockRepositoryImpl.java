package org.depromeet.spot.jpa.block.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.jpa.block.entity.BlockRowEntity;
import org.depromeet.spot.usecase.port.out.block.BlockRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockRepositoryImpl implements BlockRepository {

    private final BlockJpaRepository blockJpaRepository;
    private final BlockCustomRepository blockCustomRepository;

    @Override
    public List<Block> findAllBySection(final Long sectionId) {
        List<BlockEntity> entities = blockJpaRepository.findAllBySectionId(sectionId);
        return entities.stream().map(BlockEntity::toDomain).toList();
    }

    @Override
    public Map<Block, List<BlockRow>> findRowInfosBy(final Long sectionId) {
        Map<BlockEntity, List<BlockRowEntity>> entityMap =
                blockCustomRepository.findRowInfosBy(sectionId);
        return entityMap.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey().toDomain(),
                                entry ->
                                        entry.getValue().stream()
                                                .map(BlockRowEntity::toDomain)
                                                .toList()));
    }
}
