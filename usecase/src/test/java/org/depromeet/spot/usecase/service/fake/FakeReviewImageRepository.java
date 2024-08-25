package org.depromeet.spot.usecase.service.fake;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;

// FIXME: 구현 필요
public class FakeReviewImageRepository implements ReviewImageRepository {

    @Override
    public List<Review> findTopReviewImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return null;
    }
}
