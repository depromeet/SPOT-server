package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.ReviewImage;

public interface ReviewImageRepository {
    List<ReviewImage> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit);
}
