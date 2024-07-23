package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.image.TopReviewImage;

public interface ReviewImageRepository {

    List<TopReviewImage> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit);
}
