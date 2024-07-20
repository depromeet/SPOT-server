package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.result.BlockReviewListResult;
import org.depromeet.spot.domain.review.result.MyReviewListResult;

public interface ReviewReadUsecase {
    BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Integer page,
            Integer size);

    MyReviewListResult findMyReviewsByUserId(
            Long userId, Integer year, Integer month, Integer page, Integer size);

    List<ReviewYearMonth> findReviewMonths(Long memberId);
}
