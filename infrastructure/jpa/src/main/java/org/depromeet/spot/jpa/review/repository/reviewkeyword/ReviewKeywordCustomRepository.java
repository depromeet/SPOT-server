package org.depromeet.spot.jpa.review.repository;

import java.util.List;

import org.depromeet.spot.domain.review.result.KeywordCount;
import org.depromeet.spot.jpa.review.entity.QReviewKeywordEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ReviewKeywordCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ReviewKeywordCustomRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<KeywordCount> findTopKeywordsByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        QReviewKeywordEntity reviewKeyword = QReviewKeywordEntity.reviewKeywordEntity;

        return queryFactory
                .select(
                        Projections.constructor(
                                KeywordCount.class,
                                reviewKeyword.content,
                                reviewKeyword.count(),
                                reviewKeyword.isPositive))
                .from(reviewKeyword)
                .join(reviewKeyword.review)
                .where(
                        reviewKeyword
                                .review
                                .stadium
                                .id
                                .eq(stadiumId)
                                .and(reviewKeyword.review.block.code.eq(blockCode)))
                .groupBy(reviewKeyword.content, reviewKeyword.isPositive)
                .orderBy(reviewKeyword.count().desc())
                .limit(limit)
                .fetch();
    }
}
