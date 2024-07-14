package org.depromeet.spot.jpa.review.repository;

import static org.depromeet.spot.jpa.review.entity.QKeywordEntity.keywordEntity;
import static org.depromeet.spot.jpa.review.entity.QReviewEntity.reviewEntity;
import static org.depromeet.spot.jpa.review.entity.QReviewImageEntity.reviewImageEntity;
import static org.depromeet.spot.jpa.review.entity.QReviewKeywordEntity.reviewKeywordEntity;

import java.util.List;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.jpa.review.entity.ReviewImageEntity;
import org.depromeet.spot.jpa.review.entity.ReviewKeywordEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<ReviewEntity> findByBlockIdWithFilters(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit) {
        return queryFactory
                .selectFrom(reviewEntity)
                .where(
                        reviewEntity
                                .stadiumId
                                .eq(stadiumId)
                                .and(reviewEntity.blockId.eq(blockId))
                                .and(rowId != null ? reviewEntity.rowId.eq(rowId) : null)
                                .and(
                                        seatNumber != null
                                                ? reviewEntity.seatNumber.eq(seatNumber)
                                                : null)
                                .and(reviewEntity.deletedAt.isNull()))
                .orderBy(reviewEntity.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public long countByBlockIdWithFilters(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber) {
        return queryFactory
                .selectFrom(reviewEntity)
                .where(
                        reviewEntity
                                .stadiumId
                                .eq(stadiumId)
                                .and(reviewEntity.blockId.eq(blockId))
                                .and(rowId != null ? reviewEntity.rowId.eq(rowId) : null)
                                .and(
                                        seatNumber != null
                                                ? reviewEntity.seatNumber.eq(seatNumber)
                                                : null)
                                .and(reviewEntity.deletedAt.isNull()))
                .fetchCount();
    }

    public List<KeywordCount> findTopKeywordsByBlockId(Long stadiumId, Long blockId, int limit) {
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
                .where(
                        reviewEntity
                                .stadiumId
                                .eq(stadiumId)
                                .and(reviewEntity.blockId.eq(blockId))
                                .and(reviewEntity.deletedAt.isNull()))
                .groupBy(keywordEntity.id, keywordEntity.content)
                .orderBy(reviewKeywordEntity.count().desc())
                .limit(limit)
                .fetch();
    }

    public List<ReviewImageEntity> findImagesByReviewId(Long reviewId) {
        return queryFactory
                .selectFrom(reviewImageEntity)
                .where(
                        reviewImageEntity
                                .reviewId
                                .eq(reviewId)
                                .and(reviewImageEntity.deletedAt.isNull()))
                .fetch();
    }

    public List<ReviewKeywordEntity> findKeywordsByReviewId(Long reviewId) {
        return queryFactory
                .selectFrom(reviewKeywordEntity)
                .where(reviewKeywordEntity.reviewId.eq(reviewId))
                .fetch();
    }
}
