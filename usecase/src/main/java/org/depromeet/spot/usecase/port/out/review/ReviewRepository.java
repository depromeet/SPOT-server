package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;

public interface ReviewRepository {
    List<Review> findByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit);

    List<Review> findByUserId(Long userId, int offset, int limit, Integer year, Integer month);

    Long countByBlockId(Long stadiumId, Long blockId, Long rowId, Long seatNumber);

    Long countByUserId(Long userId, Integer year, Integer month);

    long countByUserId(Long userId);

    List<KeywordCount> findTopKeywordsByBlockId(Long stadiumId, Long blockId, int limit);

    Review save(Review review);
}
