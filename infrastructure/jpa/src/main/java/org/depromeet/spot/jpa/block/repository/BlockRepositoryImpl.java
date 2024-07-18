package org.depromeet.spot.jpa.block.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.block.BlockException.BlockNotFoundException;
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
    private final BlockRowJpaRepository blockRowJpaRepository;
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

    @Override
    public List<BlockRow> findAllByBlock(final Long blockId) {
        List<BlockRowEntity> entities =
                blockRowJpaRepository.findAllByBlockIdOrderByNumberAsc(blockId);
        return entities.stream().map(BlockRowEntity::toDomain).toList();
    }

    @Override
    public List<BlockRow> findAllByStadiumAndBlock(Long stadiumId, String blockCode) {
        List<BlockRowEntity> entities =
                blockRowJpaRepository.findAllByStadiumAndBlock(stadiumId, blockCode);
        return entities.stream().map(BlockRowEntity::toDomain).toList();
    }

    @Override
    public boolean existsById(final Long blockId) {
        return blockJpaRepository.existsById(blockId);
    }

    @Override
    public boolean existsByStadiumAndCode(Long stadiumId, String code) {
        return blockJpaRepository.existsByStadiumIdAndCode(stadiumId, code);
    }

    @Override
    public Block findById(final Long blockId) {
        BlockEntity entity =
                blockJpaRepository.findById(blockId).orElseThrow(BlockNotFoundException::new);
        return entity.toDomain();
    }

    @Override
    public Block findByStadiumAndCode(final Long stadiumId, final String code) {
        BlockEntity entity =
                blockJpaRepository
                        .findByStadiumIdAndCode(stadiumId, code)
                        .orElseThrow(BlockNotFoundException::new);
        return entity.toDomain();
    }
}
