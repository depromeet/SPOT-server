package org.depromeet.spot.domain.review.result;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.image.ReviewImage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockReviewListResult {
    private final List<Review> reviews;
    private final List<KeywordCount> topKeywords;
    private final List<ReviewImage> topReviewImages;
    private final long totalElements;
    private final int totalPages;
    private final int number;
    private final int size;
}
