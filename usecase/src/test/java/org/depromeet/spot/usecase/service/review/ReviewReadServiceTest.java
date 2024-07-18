package org.depromeet.spot.usecase.service.review;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.depromeet.spot.common.exception.review.ReviewException;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewKeyword;
import org.depromeet.spot.domain.review.ReviewListResult;
import org.depromeet.spot.usecase.service.fake.FakeReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReviewReadServiceTest {

    private ReviewReadService reviewReadService;
    private FakeReviewRepository fakeReviewRepository;

    private ReviewKeyword createReviewKeyword(
            Long id, Long reviewId, Long keywordId, Boolean isPositive) {
        return ReviewKeyword.builder()
                .id(id)
                .reviewId(reviewId)
                .keywordId(keywordId)
                .isPositive(isPositive)
                .build();
    }

    @BeforeEach
    void setUp() {
        fakeReviewRepository = new FakeReviewRepository();
        reviewReadService = new ReviewReadService(fakeReviewRepository);
    }

    @Test
    void findReviewsByBlockId는_블록별_리뷰를_정상적으로_반환한다() {
        // Given
        Review review1 =
                Review.builder()
                        .id(1L)
                        .stadiumId(1L)
                        .blockId(1L)
                        .rowId(1L)
                        .seatNumber(1L)
                        .content("Great game!")
                        .keywords(Collections.singletonList(createReviewKeyword(1L, 1L, 1L, true)))
                        .build();
        Review review2 =
                Review.builder()
                        .id(2L)
                        .stadiumId(1L)
                        .blockId(1L)
                        .rowId(2L)
                        .seatNumber(2L)
                        .content("Amazing view!")
                        .keywords(
                                Collections.singletonList(
                                        createReviewKeyword(2L, 2L, 2L, true))) // 다른 keywordId 사용
                        .build();
        fakeReviewRepository.save(review1);
        fakeReviewRepository.save(review2);

        // When
        ReviewListResult result = reviewReadService.findReviewsByBlockId(1L, 1L, null, null, 0, 10);

        // Then
        assertEquals(2, result.reviews().size());
        assertEquals(2, result.totalCount());
        assertEquals(2, result.topKeywords().size());
    }

    @Test
    void findReviewsByBlockId는_조회된_리뷰가_없을_시_NotFoundException_반환한다() {
        // Given
        Long nonExistentStadiumId = 999L;
        Long nonExistentBlockId = 999L;

        // When & Then
        assertThatThrownBy(
                        () ->
                                reviewReadService.findReviewsByBlockId(
                                        nonExistentStadiumId,
                                        nonExistentBlockId,
                                        null,
                                        null,
                                        0,
                                        10))
                .isInstanceOf(ReviewException.ReviewNotFoundException.class)
                .hasMessageContaining("No review found for blockId:" + nonExistentBlockId);
    }

    @Test
    void findReviewsByBlockId는_offset과_limit로_잘_필터링한다() {
        // Given
        for (int i = 0; i < 20; i++) {
            Review review =
                    Review.builder()
                            .id((long) i + 1)
                            .stadiumId(1L)
                            .blockId(1L)
                            .rowId(1L)
                            .seatNumber((long) i)
                            .content("Review " + i)
                            .keywords(Collections.emptyList()) // 빈 리스트로 초기화
                            .build();
            fakeReviewRepository.save(review);
        }

        // When
        ReviewListResult result = reviewReadService.findReviewsByBlockId(1L, 1L, null, null, 5, 10);

        // Then
        assertEquals(10, result.reviews().size());
        assertEquals(20, result.totalCount());
    }

    @Test
    void findMyReviews는_유저별_리뷰를_정상적으로_반환한다() {
        // Given
        Review review1 =
                Review.builder()
                        .id(1L)
                        .userId(1L)
                        .content("Great game!")
                        .keywords(Collections.singletonList(createReviewKeyword(1L, 1L, 1L, true)))
                        .build();
        Review review2 =
                Review.builder()
                        .id(2L)
                        .userId(1L)
                        .content("Amazing view!")
                        .keywords(
                                Collections.singletonList(
                                        createReviewKeyword(2L, 2L, 2L, true))) // 다른 keywordId 사용
                        .build();
        fakeReviewRepository.save(review1);
        fakeReviewRepository.save(review2);

        // When
        ReviewListResult result = reviewReadService.findMyReviews(1L, 0, 10, null, null);

        // Then
        assertEquals(2, result.reviews().size());
        assertEquals(2, result.totalCount());
        assertEquals(0, result.topKeywords().size());
    }
}
