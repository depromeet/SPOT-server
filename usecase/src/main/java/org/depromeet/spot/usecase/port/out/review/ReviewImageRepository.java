package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.Review;

public interface ReviewImageRepository {

    List<Review> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit);
}
