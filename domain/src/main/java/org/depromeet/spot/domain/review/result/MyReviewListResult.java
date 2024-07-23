package org.depromeet.spot.domain.review.result;

import java.util.List;

import org.depromeet.spot.domain.review.Review;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyReviewListResult {
    private final List<Review> reviews;
    private final long totalElements;
    private final int totalPages;
    private final int number;
    private final int size;

    public MyReviewListResult(
            List<Review> reviews, long totalElements, int totalPages, int number, int size) {
        this.reviews = reviews;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
        this.size = size;
    }
}
