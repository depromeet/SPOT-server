package org.depromeet.spot.usecase.service.review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.service.fake.FakeReviewScrapRepository;
import org.depromeet.spot.usecase.service.review.scrap.ReviewScrapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReviewScrapServiceTest {

    private ReviewScrapService reviewScrapService;
    private FakeReviewScrapRepository fakeReviewScrapRepository;

    @Mock private ReadReviewUsecase readReviewUsecase;

    @Mock private UpdateReviewUsecase updateReviewUsecase;

    @BeforeEach
    void 초기화() {
        MockitoAnnotations.openMocks(this);
        fakeReviewScrapRepository = new FakeReviewScrapRepository();
        reviewScrapService =
                new ReviewScrapService(
                        readReviewUsecase, updateReviewUsecase, fakeReviewScrapRepository);
    }

    @Test
    void 스크랩_토글_추가() {
        // given
        long memberId = 1L;
        long reviewId = 1L;
        Review mockReview = mock(Review.class);
        when(readReviewUsecase.findById(reviewId)).thenReturn(mockReview);

        // when
        boolean result = reviewScrapService.toggleScrap(memberId, reviewId);

        // then
        assertTrue(result);
        assertTrue(fakeReviewScrapRepository.existsBy(memberId, reviewId));
        verify(mockReview).addScrap();
        verify(updateReviewUsecase).updateScrapsCount(mockReview);
    }

    @Test
    void 스크랩_토글_제거() {
        // given
        long memberId = 1L;
        long reviewId = 1L;
        Review mockReview = mock(Review.class);
        when(readReviewUsecase.findById(reviewId)).thenReturn(mockReview);
        ReviewScrap scrap = ReviewScrap.builder().memberId(memberId).reviewId(reviewId).build();
        fakeReviewScrapRepository.save(scrap);

        // when
        boolean result = reviewScrapService.toggleScrap(memberId, reviewId);

        // then
        assertFalse(result);
        assertFalse(fakeReviewScrapRepository.existsBy(memberId, reviewId));
        verify(mockReview).cancelScrap();
        verify(updateReviewUsecase).updateScrapsCount(mockReview);
    }

    @Test
    void 스크랩_여부_확인() {
        // given
        long memberId = 1L;
        long reviewId = 1L;
        ReviewScrap scrap = ReviewScrap.builder().memberId(memberId).reviewId(reviewId).build();
        fakeReviewScrapRepository.save(scrap);

        // when
        boolean exists = reviewScrapService.isScraped(memberId, reviewId);

        // then
        assertTrue(exists);
    }

    @Test
    void 존재하지_않는_스크랩_여부_확인() {
        // given
        long memberId = 1L;
        long reviewId = 1L;

        // when
        boolean exists = reviewScrapService.isScraped(memberId, reviewId);

        // then
        assertFalse(exists);
    }

    @Test
    void 스크랩_추가() {
        // given
        long memberId = 1L;
        long reviewId = 1L;
        Review mockReview = mock(Review.class);

        // when
        reviewScrapService.addScrap(memberId, reviewId, mockReview);

        // then
        assertTrue(fakeReviewScrapRepository.existsBy(memberId, reviewId));
        verify(mockReview).addScrap();
        verify(updateReviewUsecase).updateScrapsCount(mockReview);
    }

    @Test
    void 스크랩_취소() {
        // given
        long memberId = 1L;
        long reviewId = 1L;
        Review mockReview = mock(Review.class);
        ReviewScrap scrap = ReviewScrap.builder().memberId(memberId).reviewId(reviewId).build();
        fakeReviewScrapRepository.save(scrap);

        // when
        reviewScrapService.cancelScrap(memberId, reviewId, mockReview);

        // then
        assertFalse(fakeReviewScrapRepository.existsBy(memberId, reviewId));
        verify(mockReview).cancelScrap();
        verify(updateReviewUsecase).updateScrapsCount(mockReview);
    }
}
