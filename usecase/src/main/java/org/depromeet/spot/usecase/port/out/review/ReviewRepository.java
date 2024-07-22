package org.depromeet.spot.usecase.port.out.review;

import java.util.Optional;

import org.depromeet.spot.domain.review.Review;

public interface ReviewRepository {
    Review save(Review review);

    Optional<Review> findById(Long id);

    void delete(Review review);

    long countByUserId(Long userId);

    //    ReviewImage saveReviewImage(ReviewImage reviewImage);
    //
    //    Optional<Keyword> findKeywordByContent(String content);
    //
    //    Keyword saveKeyword(Keyword keyword);
    //
    //    ReviewKeyword saveReviewKeyword(ReviewKeyword reviewKeyword);

    //    void updateBlockTopKeyword(Long blockId, Long keywordId);
    //
    ////    long countByUserId(Long userId);
    //
    //    Review addImages(Long reviewId, List<ReviewImage> images);
    //
    //    Review addKeywords(Long reviewId, List<ReviewKeyword> keywords);
}
