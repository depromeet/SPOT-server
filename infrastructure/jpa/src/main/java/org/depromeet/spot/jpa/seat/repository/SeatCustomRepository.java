package org.depromeet.spot.jpa.seat.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static org.depromeet.spot.jpa.block.entity.QBlockEntity.blockEntity;
import static org.depromeet.spot.jpa.block.entity.QBlockRowEntity.blockRowEntity;
import static org.depromeet.spot.jpa.seat.entity.QSeatEntity.seatEntity;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.jpa.seat.entity.SeatEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Map<BlockEntity, List<SeatEntity>> findBlockSeatsBy(final Long sectionId) {
        return queryFactory
                .from(seatEntity)
                .join(blockEntity)
                .on(seatEntity.blockId.eq(blockEntity.id))
                .join(blockRowEntity)
                .on(seatEntity.rowId.eq(blockRowEntity.id))
                .where(seatEntity.sectionId.eq(sectionId))
                .transform(groupBy(blockEntity).as(list(seatEntity)));
    }
}
