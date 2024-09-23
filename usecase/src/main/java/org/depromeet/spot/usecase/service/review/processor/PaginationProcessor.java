package org.depromeet.spot.usecase.service.review.processor;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;

public interface PaginationProcessor {

    String getCursor(Review review, SortCriteria sortBy);
}
