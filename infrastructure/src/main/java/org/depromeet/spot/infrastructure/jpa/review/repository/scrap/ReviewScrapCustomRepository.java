package org.depromeet.spot.infrastructure.jpa.review.repository.scrap;

import static org.depromeet.spot.infrastructure.jpa.review.entity.QReviewEntity.reviewEntity;
import static org.depromeet.spot.infrastructure.jpa.review.entity.keyword.QKeywordEntity.keywordEntity;
import static org.depromeet.spot.infrastructure.jpa.review.entity.keyword.QReviewKeywordEntity.reviewKeywordEntity;
import static org.depromeet.spot.infrastructure.jpa.review.entity.scrap.QReviewScrapEntity.reviewScrapEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewScrapCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<ReviewEntity> findScrappedReviewsByMemberId(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad,
            String cursor,
            SortCriteria sortBy,
            Integer size) {

        JPAQuery<ReviewEntity> query =
                queryFactory
                        .selectDistinct(reviewEntity)
                        .from(reviewEntity)
                        .join(reviewScrapEntity)
                        .on(reviewScrapEntity.reviewId.eq(reviewEntity.id))
                        .leftJoin(reviewKeywordEntity)
                        .on(reviewKeywordEntity.review.eq(reviewEntity))
                        .leftJoin(keywordEntity)
                        .on(keywordEntity.id.eq(reviewKeywordEntity.keywordId))
                        .where(
                                reviewScrapEntity.memberId.eq(memberId),
                                stadiumIdEq(stadiumId),
                                monthsIn(months),
                                keywordsIn(good, true),
                                keywordsIn(bad, false),
                                cursorCondition(cursor, sortBy))
                        .orderBy(reviewEntity.dateTime.desc(), reviewEntity.id.desc())
                        .limit(size);

        return query.fetch();
    }

    public Long getTotalCount(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad) {

        return queryFactory
                .select(reviewEntity.countDistinct())
                .from(reviewEntity)
                .join(reviewScrapEntity)
                .on(reviewScrapEntity.reviewId.eq(reviewEntity.id))
                .leftJoin(reviewKeywordEntity)
                .on(reviewKeywordEntity.review.eq(reviewEntity))
                .leftJoin(keywordEntity)
                .on(keywordEntity.id.eq(reviewKeywordEntity.keywordId))
                .where(
                        reviewScrapEntity.memberId.eq(memberId),
                        stadiumIdEq(stadiumId),
                        monthsIn(months),
                        keywordsIn(good, true),
                        keywordsIn(bad, false))
                .fetchOne();
    }

    private BooleanExpression stadiumIdEq(Long stadiumId) {
        return stadiumId != null ? reviewEntity.stadium.id.eq(stadiumId) : null;
    }

    private BooleanExpression monthsIn(List<Integer> months) {
        if (months == null || months.isEmpty()) {
            return null;
        }
        return reviewEntity.dateTime.month().in(months);
    }

    private BooleanExpression keywordsIn(List<String> keywords, boolean isPositive) {
        if (keywords == null || keywords.isEmpty()) {
            return null;
        }
        return keywordEntity.content.in(keywords).and(keywordEntity.isPositive.eq(isPositive));
    }

    private BooleanExpression cursorCondition(String cursor, SortCriteria sortBy) {
        if (cursor == null) {
            return null;
        }

        String[] parts = cursor.split("_");
        if (parts.length != 3) {
            return null;
        }

        LocalDateTime dateTime = LocalDateTime.parse(parts[0]);
        Integer likesCount = Integer.parseInt(parts[1]);
        Long id = Long.parseLong(parts[2]);

        switch (sortBy) {
            case LIKES_COUNT:
                return reviewEntity
                        .likesCount
                        .lt(likesCount)
                        .or(
                                reviewEntity
                                        .likesCount
                                        .eq(likesCount)
                                        .and(
                                                reviewEntity
                                                        .dateTime
                                                        .lt(dateTime)
                                                        .or(
                                                                reviewEntity
                                                                        .dateTime
                                                                        .eq(dateTime)
                                                                        .and(
                                                                                reviewEntity.id.lt(
                                                                                        id)))));
            case DATE_TIME:
            default:
                return reviewEntity
                        .dateTime
                        .lt(dateTime)
                        .or(reviewEntity.dateTime.eq(dateTime).and(reviewEntity.id.lt(id)));
        }
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(SortCriteria sortBy) {
        switch (sortBy) {
            case LIKES_COUNT:
                return new OrderSpecifier<?>[] {
                    reviewEntity.likesCount.desc(),
                    reviewEntity.dateTime.desc(),
                    reviewEntity.id.desc()
                };
            case DATE_TIME:
            default:
                return new OrderSpecifier<?>[] {
                    reviewEntity.dateTime.desc(), reviewEntity.id.desc()
                };
        }
    }

    public String getNextCursor(List<ReviewEntity> reviews) {
        if (reviews.isEmpty()) {
            return null;
        }
        ReviewEntity lastReview = reviews.get(reviews.size() - 1);
        return lastReview.getDateTime().toString()
                + "_"
                + lastReview.getLikesCount()
                + "_"
                + lastReview.getId();
    }
}
