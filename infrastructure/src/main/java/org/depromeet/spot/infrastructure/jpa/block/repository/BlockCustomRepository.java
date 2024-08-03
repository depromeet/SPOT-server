package org.depromeet.spot.infrastructure.jpa.block.repository;

import static com.querydsl.core.group.GroupBy.list;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.infrastructure.jpa.block.entity.BlockEntity;
import org.depromeet.spot.infrastructure.jpa.block.entity.BlockRowEntity;
import org.depromeet.spot.infrastructure.jpa.block.entity.QBlockEntity;
import org.depromeet.spot.infrastructure.jpa.block.entity.QBlockRowEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Map<BlockEntity, List<BlockRowEntity>> findRowInfosBy(final Long sectionId) {
        return queryFactory
                .from(QBlockRowEntity.blockRowEntity)
                .join(QBlockEntity.blockEntity)
                .on(QBlockRowEntity.blockRowEntity.block.id.eq(QBlockEntity.blockEntity.id))
                .where(QBlockEntity.blockEntity.sectionId.eq(sectionId))
                .orderBy(QBlockRowEntity.blockRowEntity.number.asc())
                .transform(
                        GroupBy.groupBy(QBlockEntity.blockEntity)
                                .as(list(QBlockRowEntity.blockRowEntity)));
    }
}
