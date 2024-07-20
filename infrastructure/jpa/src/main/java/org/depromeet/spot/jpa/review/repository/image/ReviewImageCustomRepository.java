package org.depromeet.spot.jpa.review.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.jpa.review.entity.QReviewImageEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ReviewImageCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ReviewImageCustomRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<ReviewImage> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        QReviewImageEntity reviewImage = QReviewImageEntity.reviewImageEntity;

        return queryFactory
                .selectFrom(reviewImage)
                .join(reviewImage.review)
                .where(
                        reviewImage
                                .review
                                .stadium
                                .id
                                .eq(stadiumId)
                                .and(reviewImage.review.block.code.eq(blockCode)))
                .orderBy(reviewImage.review.createdAt.desc())
                .limit(limit)
                .fetch()
                .stream()
                .map(QReviewImageEntity::toDomain)
                .collect(Collectors.toList());
    }
}
