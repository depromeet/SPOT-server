package org.depromeet.spot.infrastructure.jpa.review.repository.image;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements ReviewImageRepository {

    private final ReviewImageCustomRepository reviewImageCustomRepository;

    @Override
    public List<Review> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        List<ReviewEntity> topReviews =
                reviewImageCustomRepository.findTopReviewsWithImagesByStadiumIdAndBlockCode(
                        stadiumId, blockCode, limit);

        return topReviews.stream()
                .map(ReviewEntity::toDomain)
                .map(review -> review.withLimitedImages(1))
                .collect(Collectors.toList());
    }
}
