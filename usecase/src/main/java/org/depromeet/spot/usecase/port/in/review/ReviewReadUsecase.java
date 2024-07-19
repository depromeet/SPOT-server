package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.review.BlockReviewListResult;
import org.depromeet.spot.domain.review.MyReviewListResult;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.springframework.data.domain.Pageable;

public interface ReviewReadUsecase {
    BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable);

    MyReviewListResult findMyReviewsByUserId(
            Long userId, Integer year, Integer month, Pageable pageable);

    List<ReviewYearMonth> findReviewMonths(Long memberId);
}
