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

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
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

        BooleanBuilder builder = buildConditions(memberId, stadiumId, months, good, bad);

        OrderSpecifier<?>[] orderBy = getOrderBy(sortBy);
        BooleanExpression cursorCondition = getCursorCondition(sortBy, cursor);

        if (cursorCondition != null) {
            builder.and(cursorCondition);
        }

        return queryFactory
                .selectDistinct(reviewEntity)
                .from(reviewEntity)
                .join(reviewScrapEntity)
                .on(reviewScrapEntity.reviewId.eq(reviewEntity.id))
                .leftJoin(reviewKeywordEntity)
                .on(reviewKeywordEntity.review.eq(reviewEntity))
                .leftJoin(keywordEntity)
                .on(keywordEntity.id.eq(reviewKeywordEntity.keywordId))
                .where(builder)
                .orderBy(orderBy)
                .limit(size)
                .fetch();
    }

    public Long getTotalCount(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad) {

        BooleanBuilder builder = buildConditions(memberId, stadiumId, months, good, bad);

        return queryFactory
                .select(reviewEntity.countDistinct())
                .from(reviewEntity)
                .join(reviewScrapEntity)
                .on(reviewScrapEntity.reviewId.eq(reviewEntity.id))
                .leftJoin(reviewKeywordEntity)
                .on(reviewKeywordEntity.review.eq(reviewEntity))
                .leftJoin(keywordEntity)
                .on(keywordEntity.id.eq(reviewKeywordEntity.keywordId))
                .where(builder)
                .fetchOne();
    }

    private BooleanBuilder buildConditions(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(reviewScrapEntity.memberId.eq(memberId));
        builder.and(stadiumIdEq(stadiumId));
        builder.and(monthsIn(months));
        builder.and(keywordsIn(good, true));
        builder.and(keywordsIn(bad, false));

        return builder;
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

    private BooleanExpression getCursorCondition(SortCriteria sortBy, String cursor) {
        if (cursor == null) {
            return null;
        }

        String[] parts = cursor.split("_");

        LocalDateTime dateTime;
        Long id;

        switch (sortBy) {
            case LIKES_COUNT:
                if (parts.length != 3) return null;
                int likeCount = Integer.parseInt(parts[0]);
                dateTime = LocalDateTime.parse(parts[1]);
                id = Long.parseLong(parts[2]);
                return reviewEntity
                        .likesCount
                        .lt(likeCount)
                        .or(
                                reviewEntity
                                        .likesCount
                                        .eq(likeCount)
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
                if (parts.length != 2) return null;
                dateTime = LocalDateTime.parse(parts[0]);
                id = Long.parseLong(parts[1]);
                return reviewEntity
                        .dateTime
                        .lt(dateTime)
                        .or(reviewEntity.dateTime.eq(dateTime).and(reviewEntity.id.lt(id)));
        }
    }

    private OrderSpecifier<?>[] getOrderBy(SortCriteria sortBy) {
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
}
