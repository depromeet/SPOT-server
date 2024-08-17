package org.depromeet.spot.domain.review;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewLikesException;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void review_builder에_공감수를_지정하지_않으면_0으로_할당된다() {
        // given
        // when
        Review review = Review.builder().build();

        // then
        assertEquals(review.getLikesCount(), 0);
    }

    @Test
    void review_builder에_공감수를_음수로_지정하면_에러를_반환한다() {
        // given
        int likesCount = -99;

        // when
        // then
        assertThatThrownBy(() -> Review.builder().likesCount(likesCount).build())
                .isInstanceOf(InvalidReviewLikesException.class);
    }
}
