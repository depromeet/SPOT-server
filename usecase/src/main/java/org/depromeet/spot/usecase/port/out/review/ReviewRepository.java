package org.depromeet.spot.usecase.port.out.review;

import java.util.Optional;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;

public interface ReviewRepository {
    Review save(Review review);

    ReviewImage saveReviewImage(ReviewImage reviewImage);

    Optional<Keyword> findKeywordByContent(String content);

    Keyword saveKeyword(Keyword keyword);

    ReviewKeyword saveReviewKeyword(ReviewKeyword reviewKeyword);

    void updateBlockTopKeyword(Long blockId, Long keywordId);

    long countByUserId(Long userId);
}
