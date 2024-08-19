package org.depromeet.spot.infrastructure.jpa.review.repository;

import static org.depromeet.spot.infrastructure.jpa.block.entity.QBlockEntity.blockEntity;
import static org.depromeet.spot.infrastructure.jpa.block.entity.QBlockRowEntity.blockRowEntity;
import static org.depromeet.spot.infrastructure.jpa.member.entity.QLevelEntity.levelEntity;
import static org.depromeet.spot.infrastructure.jpa.member.entity.QMemberEntity.memberEntity;
import static org.depromeet.spot.infrastructure.jpa.review.entity.QReviewEntity.reviewEntity;
import static org.depromeet.spot.infrastructure.jpa.review.entity.keyword.QReviewKeywordEntity.reviewKeywordEntity;
import static org.depromeet.spot.infrastructure.jpa.seat.entity.QSeatEntity.seatEntity;
import static org.depromeet.spot.infrastructure.jpa.section.entity.QSectionEntity.sectionEntity;
import static org.depromeet.spot.infrastructure.jpa.stadium.entity.QStadiumEntity.stadiumEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.LocationInfo;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<ReviewEntity> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            int size) {

        BooleanBuilder builder =
                buildConditions(stadiumId, blockCode, rowNumber, seatNumber, year, month);

        OrderSpecifier<?>[] orderBy = getOrderBy(sortBy);
        BooleanExpression cursorCondition = getCursorCondition(sortBy, cursor);

        if (cursorCondition != null) {
            builder.and(cursorCondition);
        }

        return queryFactory
                .selectFrom(reviewEntity)
                .leftJoin(reviewEntity.stadium, stadiumEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.section, sectionEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.block, blockEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.seat, seatEntity)
                .fetchJoin()
                .leftJoin(seatEntity.row, blockRowEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.keywords, reviewKeywordEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.member, memberEntity)
                .fetchJoin()
                .leftJoin(memberEntity.level, levelEntity)
                .fetchJoin()
                .where(builder)
                .orderBy(orderBy)
                .limit(size)
                .fetch();
    }

    public List<ReviewEntity> findAllByUserId(
            Long userId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            int size) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(reviewEntity.member.id.eq(userId));
        builder.and(eqYear(year));
        builder.and(eqMonth(month));
        builder.and(reviewEntity.deletedAt.isNull());

        OrderSpecifier<?>[] orderBy = getOrderBy(sortBy);
        BooleanExpression cursorCondition = getCursorCondition(sortBy, cursor);

        if (cursorCondition != null) {
            builder.and(cursorCondition);
        }

        return queryFactory
                .selectFrom(reviewEntity)
                .leftJoin(reviewEntity.stadium, stadiumEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.section, sectionEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.block, blockEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.row, blockRowEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.seat, seatEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.keywords, reviewKeywordEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.member, memberEntity)
                .fetchJoin()
                .leftJoin(memberEntity.level, levelEntity)
                .fetchJoin()
                .where(builder)
                .orderBy(orderBy)
                .limit(size)
                .fetch();
    }

    public LocationInfo findLocationInfoByStadiumIdAndBlockCode(Long stadiumId, String blockCode) {
        return queryFactory
                .select(
                        Projections.constructor(
                                LocationInfo.class,
                                stadiumEntity.name,
                                sectionEntity.name,
                                blockEntity.code))
                .from(stadiumEntity)
                .join(blockEntity)
                .on(blockEntity.stadiumId.eq(stadiumEntity.id))
                .join(sectionEntity)
                .on(sectionEntity.id.eq(blockEntity.sectionId))
                .where(stadiumEntity.id.eq(stadiumId).and(blockEntity.code.eq(blockCode)))
                .fetchOne();
    }

    private BooleanBuilder buildConditions(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(reviewEntity.stadium.id.eq(stadiumId));
        builder.and(reviewEntity.block.code.eq(blockCode));
        builder.and(eqRowNumber(rowNumber));
        builder.and(eqSeatNumber(seatNumber));
        builder.and(eqYear(year));
        builder.and(eqMonth(month));
        builder.and(reviewEntity.deletedAt.isNull());

        return builder;
    }

    private BooleanExpression eqRowNumber(Integer rowNumber) {
        if (rowNumber != null) {
            return reviewEntity.row.number.eq(rowNumber);
        }
        return null;
    }

    private BooleanExpression eqSeatNumber(Integer seatNumber) {
        if (seatNumber != null) {
            return reviewEntity.seat.seatNumber.eq(seatNumber);
        }
        return null;
    }

    private BooleanExpression eqYear(Integer year) {
        if (year != null) {
            return reviewEntity.dateTime.year().eq(year);
        }
        return null;
    }

    private BooleanExpression eqMonth(Integer month) {
        if (month != null) {
            return reviewEntity.dateTime.month().eq(month);
        }
        return null;
    }

    private OrderSpecifier<?>[] getOrderBy(SortCriteria sortBy) {
        switch (sortBy) {
                // TODO: 좋아요 컬럼 반영 시 주석 해제
                //            case LIKES_COUNT:
                //                return new OrderSpecifier<?>[] {
                //                    reviewEntity.likesCount.desc().nullsLast(),
                //                    reviewEntity.dateTime.desc()
                //                };
            case DATE_TIME:
            default:
                return new OrderSpecifier<?>[] {reviewEntity.dateTime.desc()};
        }
    }

    private BooleanExpression getCursorCondition(SortCriteria sortBy, String cursor) {
        if (cursor == null) {
            return null;
        }

        String[] parts = cursor.split("_");

        switch (sortBy) {
                // TODO: 좋아요 컬럼 반영 시 주석 해제
                //            case LIKES_COUNT:
                //                if (parts.length != 3) return null;
                //                int likeCount = Integer.parseInt(parts[0]);
                //                LocalDateTime dateTime = LocalDateTime.parse(parts[1]);
                //                Long id = Long.parseLong(parts[2]);
                //                return reviewEntity.likesCount.lt(likeCount)
                //                    .or(reviewEntity.likesCount.eq(likeCount)
                //                        .and(reviewEntity.dateTime.lt(dateTime)
                //                            .or(reviewEntity.dateTime.eq(dateTime)
                //                                .and(reviewEntity.id.lt(id)))));
            case DATE_TIME:
            default:
                if (parts.length != 2) return null;
                LocalDateTime dateTime = LocalDateTime.parse(parts[0]);
                Long id = Long.parseLong(parts[1]);
                return reviewEntity
                        .dateTime
                        .lt(dateTime)
                        .or(reviewEntity.dateTime.eq(dateTime).and(reviewEntity.id.lt(id)));
        }
    }
}
