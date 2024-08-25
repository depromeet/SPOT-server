package org.depromeet.spot.usecase.port.out.review;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.scrap.ReviewScrap;

public interface ReviewScrapRepository {

    List<Review> findScrappedReviewsByMemberId(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad,
            String cursor,
            SortCriteria sortBy,
            Integer size);

    Long getTotalCount(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad);

    boolean existsBy(long memberId, long reviewId);

    long countByReview(long reviewId);

    void deleteBy(long memberId, long reviewId);

    void save(ReviewScrap like);

    Map<Long, Boolean> existsByMemberIdAndReviewIds(Long memberId, List<Long> reviewIds);
}
