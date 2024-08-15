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

import java.util.List;

import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ReviewEntity> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable) {
        BooleanBuilder builder =
                buildConditions(stadiumId, blockCode, rowNumber, seatNumber, year, month);
        List<ReviewEntity> results =
                queryFactory
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
                        .orderBy(reviewEntity.dateTime.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total = queryFactory.selectFrom(reviewEntity).where(builder).fetch().size();

        return new PageImpl<>(results, pageable, total);
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
}
