package org.depromeet.spot.domain.review.image;

import org.depromeet.spot.domain.review.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewImage {
    private final Long id;
    private final Review review;
    private final String url;

    public static ReviewImage create(Long id, Review review, String url) {
        return new ReviewImage(id, review, url);
    }
}
