package org.depromeet.spot.usecase.service.review.processor;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaginationProcessorImpl implements PaginationProcessor {

    public String getCursor(Review review, SortCriteria sortBy) {
        switch (sortBy) {
            case LIKES_COUNT:
                return review.getLikesCount()
                        + "_"
                        + review.getDateTime().toString()
                        + "_"
                        + review.getId();
            case DATE_TIME:
            default:
                return review.getDateTime().toString() + "_" + review.getId();
        }
    }
}
