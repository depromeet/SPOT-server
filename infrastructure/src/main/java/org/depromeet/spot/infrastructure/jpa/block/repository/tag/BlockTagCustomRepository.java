package org.depromeet.spot.infrastructure.jpa.block.repository.tag;

import static org.depromeet.spot.infrastructure.jpa.block.entity.QBlockEntity.blockEntity;
import static org.depromeet.spot.infrastructure.jpa.block.entity.QBlockTagEntity.blockTagEntity;
import static org.depromeet.spot.infrastructure.jpa.hashtag.entity.QHashTagEntity.hashTagEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.infrastructure.jpa.block.entity.BlockEntity;
import org.depromeet.spot.infrastructure.jpa.block.entity.BlockTagEntity;
import org.depromeet.spot.infrastructure.jpa.hashtag.entity.HashTagEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockTagCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Map<HashTagEntity, List<BlockEntity>> findAllByStadium(Long stadiumId) {
        List<BlockTagEntity> blockTagEntities =
                queryFactory
                        .selectFrom(blockTagEntity)
                        .join(blockTagEntity.block, blockEntity)
                        .fetchJoin()
                        .join(blockTagEntity.hashTag, hashTagEntity)
                        .fetchJoin()
                        .where(blockEntity.stadiumId.eq(stadiumId))
                        .fetch();

        return blockTagEntities.stream()
                .collect(
                        Collectors.groupingBy(
                                BlockTagEntity::getHashTag,
                                Collectors.mapping(BlockTagEntity::getBlock, Collectors.toList())));
    }
}
