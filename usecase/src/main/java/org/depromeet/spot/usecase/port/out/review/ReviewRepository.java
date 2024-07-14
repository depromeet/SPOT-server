package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;

public interface ReviewRepository {
    List<Review> findByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit);

    int countByBlockId(Long stadiumId, Long blockId, Long rowId, Long seatNumber);

    List<KeywordCount> findTopKeywordsByBlockId(Long stadiumId, Long blockId, int limit);
}
