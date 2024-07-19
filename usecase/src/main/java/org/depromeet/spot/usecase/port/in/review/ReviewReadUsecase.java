package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.review.BlockReviewListResult;
import org.depromeet.spot.domain.review.MyReviewListResult;
import org.depromeet.spot.domain.review.ReviewYearMonth;

public interface ReviewReadUsecase {
    BlockReviewListResult findReviewsByBlockId(
            Long stadiumId,
            String blockCode,
            Long rowNumber,
            Long seatNumber,
            int offset,
            int limit);

    MyReviewListResult findMyReviews(
            Long userId, int offset, int limit, Integer year, Integer month);

    List<ReviewYearMonth> findReviewMonths(Long memberId);
}
