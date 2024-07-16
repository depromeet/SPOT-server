package org.depromeet.spot.jpa.block.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static org.depromeet.spot.jpa.block.entity.QBlockEntity.blockEntity;
import static org.depromeet.spot.jpa.block.entity.QBlockRowEntity.blockRowEntity;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.jpa.block.entity.BlockRowEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Map<BlockEntity, List<BlockRowEntity>> findRowInfosBy(final Long sectionId) {
        return queryFactory
                .from(blockRowEntity)
                .join(blockEntity)
                .on(blockRowEntity.block.id.eq(blockEntity.id))
                .where(blockEntity.sectionId.eq(sectionId))
                .orderBy(blockRowEntity.number.asc())
                .transform(groupBy(blockEntity).as(list(blockRowEntity)));
    }
}
