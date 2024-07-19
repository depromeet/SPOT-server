package org.depromeet.spot.jpa.review.repository;

import static org.depromeet.spot.jpa.block.entity.QBlockEntity.blockEntity;
import static org.depromeet.spot.jpa.block.entity.QBlockRowEntity.blockRowEntity;
import static org.depromeet.spot.jpa.review.entity.QKeywordEntity.keywordEntity;
import static org.depromeet.spot.jpa.review.entity.QReviewEntity.reviewEntity;
import static org.depromeet.spot.jpa.review.entity.QReviewImageEntity.reviewImageEntity;
import static org.depromeet.spot.jpa.review.entity.QReviewKeywordEntity.reviewKeywordEntity;
import static org.depromeet.spot.jpa.seat.entity.QSeatEntity.seatEntity;
import static org.depromeet.spot.jpa.section.entity.QSectionEntity.sectionEntity;
import static org.depromeet.spot.jpa.stadium.entity.QStadiumEntity.stadiumEntity;

import java.util.List;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.jpa.review.entity.KeywordEntity;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.jpa.review.entity.ReviewImageEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<Review> findByBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            int offset,
            int limit) {
        List<ReviewEntity> reviewEntities =
                queryFactory
                        .selectFrom(reviewEntity)
                        .join(seatEntity)
                        .on(reviewEntity.seatId.eq(seatEntity.id))
                        .join(blockEntity)
                        .on(seatEntity.block.id.eq(blockEntity.id))
                        .join(blockRowEntity)
                        .on(seatEntity.row.id.eq(blockRowEntity.id))
                        .join(sectionEntity)
                        .on(blockEntity.sectionId.eq(sectionEntity.id))
                        .join(stadiumEntity)
                        .on(sectionEntity.stadiumId.eq(stadiumEntity.id))
                        .leftJoin(reviewImageEntity)
                        .on(reviewEntity.id.eq(reviewImageEntity.reviewId))
                        .leftJoin(reviewKeywordEntity)
                        .on(reviewEntity.id.eq(reviewKeywordEntity.reviewId))
                        .leftJoin(keywordEntity)
                        .on(reviewKeywordEntity.keywordId.eq(keywordEntity.id))
                        .where(
                                stadiumEntity
                                        .id
                                        .eq(stadiumId)
                                        .and(blockEntity.code.eq(blockCode))
                                        .and(
                                                rowNumber != null
                                                        ? blockRowEntity.number.eq(rowNumber)
                                                        : null)
                                        .and(
                                                seatNumber != null
                                                        ? seatEntity.seatNumber.eq(seatNumber)
                                                        : null)
                                        .and(reviewEntity.deletedAt.isNull()))
                        .orderBy(reviewEntity.createdAt.desc())
                        .offset(offset)
                        .limit(limit)
                        .fetch();

        return reviewEntities.stream()
                .map(
                        reviewEntity -> {
                            List<ReviewImageEntity> images =
                                    queryFactory
                                            .selectFrom(reviewImageEntity)
                                            .where(
                                                    reviewImageEntity.reviewId.eq(
                                                            reviewEntity.getId()))
                                            .fetch();

                            List<KeywordEntity> keywords =
                                    queryFactory
                                            .selectFrom(keywordEntity)
                                            .join(reviewKeywordEntity)
                                            .on(keywordEntity.id.eq(reviewKeywordEntity.keywordId))
                                            .where(
                                                    reviewKeywordEntity.reviewId.eq(
                                                            reviewEntity.getId()))
                                            .fetch();

                            return ReviewEntity.createReviewWithDetails(
                                    reviewEntity, images, keywords);
                        })
                .toList();
    }

    public List<ReviewEntity> findByUserIdWithFilters(
            Long userId, int offset, int limit, Integer year, Integer month) {
        return queryFactory
                .selectFrom(reviewEntity)
                .where(
                        reviewEntity
                                .userId
                                .eq(userId)
                                .and(year != null ? reviewEntity.createdAt.year().eq(year) : null)
                                .and(
                                        month != null
                                                ? reviewEntity.createdAt.month().eq(month)
                                                : null)
                                .and(reviewEntity.deletedAt.isNull()))
                .orderBy(reviewEntity.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public long countByBlockIdWithFilters(Long stadiumId, Long blockId, Long rowId, Long seatId) {
        return queryFactory
                .selectFrom(reviewEntity)
                .join(seatEntity)
                .on(reviewEntity.seatId.eq(seatEntity.id))
                .where(
                        reviewEntity
                                .stadiumId
                                .eq(stadiumId)
                                .and(reviewEntity.blockId.eq(blockId))
                                .and(rowId != null ? reviewEntity.rowId.eq(rowId) : null)
                                .and(seatId != null ? reviewEntity.seatId.eq(seatId) : null)
                                .and(reviewEntity.deletedAt.isNull()))
                .fetchCount();
    }

    public long countByUserIdWithFilters(Long userId, Integer year, Integer month) {
        return queryFactory
                .selectFrom(reviewEntity)
                .where(
                        reviewEntity
                                .userId
                                .eq(userId)
                                .and(year != null ? reviewEntity.createdAt.year().eq(year) : null)
                                .and(
                                        month != null
                                                ? reviewEntity.createdAt.month().eq(month)
                                                : null)
                                .and(reviewEntity.deletedAt.isNull()))
                .fetchCount();
    }

    public List<KeywordCount> findTopKeywordsByBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return queryFactory
                .select(
                        Projections.constructor(
                                KeywordCount.class,
                                keywordEntity.content,
                                reviewKeywordEntity.count().as("count")))
                .from(reviewKeywordEntity)
                .join(keywordEntity)
                .on(reviewKeywordEntity.keywordId.eq(keywordEntity.id))
                .join(reviewEntity)
                .on(reviewKeywordEntity.reviewId.eq(reviewEntity.id))
                .join(seatEntity)
                .on(reviewEntity.seatId.eq(seatEntity.id))
                .join(blockEntity)
                .on(seatEntity.block.id.eq(blockEntity.id))
                .where(
                        seatEntity
                                .stadium
                                .id
                                .eq(stadiumId)
                                .and(blockEntity.code.eq(blockCode))
                                .and(reviewEntity.deletedAt.isNull()))
                .groupBy(keywordEntity.id, keywordEntity.content)
                .orderBy(reviewKeywordEntity.count().desc())
                .limit(limit)
                .fetch();
    }

    public List<ReviewImageEntity> findImagesByReviewIds(List<Long> reviewIds) {
        return queryFactory
                .selectFrom(reviewImageEntity)
                .where(
                        reviewImageEntity
                                .reviewId
                                .in(reviewIds)
                                .and(reviewImageEntity.deletedAt.isNull()))
                .fetch();
    }

    public List<KeywordEntity> findKeywordsByReviewIds(List<Long> reviewIds) {
        return queryFactory
                .selectFrom(keywordEntity)
                .join(reviewKeywordEntity)
                .on(keywordEntity.id.eq(reviewKeywordEntity.keywordId))
                .where(reviewKeywordEntity.reviewId.in(reviewIds))
                .fetch();
    }

    public List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ReviewYearMonth.class,
                                reviewEntity.createdAt.year(),
                                reviewEntity.createdAt.month()))
                .from(reviewEntity)
                .where(reviewEntity.userId.eq(memberId).and(reviewEntity.deletedAt.isNull()))
                .groupBy(reviewEntity.createdAt.year(), reviewEntity.createdAt.month())
                .orderBy(
                        reviewEntity.createdAt.year().desc(), reviewEntity.createdAt.month().desc())
                .fetch();
    }
}
