package org.depromeet.spot.usecase.port.in.review;

import org.depromeet.spot.domain.review.ReviewListResult;

public interface ReviewReadUsecase {
    ReviewListResult findReviewsByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit);
}
