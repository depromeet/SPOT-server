package org.depromeet.spot.infrastructure.jpa.block.repository.tag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.hashtag.HashTag;
import org.depromeet.spot.infrastructure.jpa.block.entity.BlockEntity;
import org.depromeet.spot.infrastructure.jpa.hashtag.entity.HashTagEntity;
import org.depromeet.spot.usecase.port.out.block.BlockTagRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockTagRepositoryImpl implements BlockTagRepository {

    private final BlockTagCustomRepository blockTagCustomRepository;

    @Override
    public Map<HashTag, List<Block>> findAllByStadium(Long stadiumId) {
        Map<HashTagEntity, List<BlockEntity>> entities =
                blockTagCustomRepository.findAllByStadium(stadiumId);
        return entities.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey().toDomain(),
                                entry ->
                                        entry.getValue().stream()
                                                .map(BlockEntity::toDomain)
                                                .toList()));
    }
}
