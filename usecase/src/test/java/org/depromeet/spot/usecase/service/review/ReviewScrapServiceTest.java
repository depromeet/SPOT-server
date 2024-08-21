package org.depromeet.spot.usecase.service.review;

import static org.junit.jupiter.api.Assertions.*;

import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.usecase.service.fake.FakeReviewScrapRepository;
import org.depromeet.spot.usecase.service.review.scrap.ReviewScrapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReviewScrapServiceTest {

    private ReviewScrapService reviewScrapService;
    private FakeReviewScrapRepository fakeReviewScrapRepository;

    @BeforeEach
    void init() {
        fakeReviewScrapRepository = new FakeReviewScrapRepository();
        reviewScrapService = new ReviewScrapService(fakeReviewScrapRepository);
    }

    @Test
    void 스크랩_추가() {
        // given
        long memberId = 1L;
        long reviewId = 1L;

        // when
        boolean result = reviewScrapService.toggleScrap(memberId, reviewId);

        // then
        assertTrue(result);
        assertTrue(fakeReviewScrapRepository.existsBy(memberId, reviewId));
    }

    @Test
    void 스크랩_제거() {
        // given
        long memberId = 1L;
        long reviewId = 1L;
        ReviewScrap scrap = ReviewScrap.builder().memberId(memberId).reviewId(reviewId).build();
        fakeReviewScrapRepository.save(scrap);

        // when
        boolean result = reviewScrapService.toggleScrap(memberId, reviewId);

        // then
        assertFalse(result);
        assertFalse(fakeReviewScrapRepository.existsBy(memberId, reviewId));
    }

    @Test
    void 스크랩_개수_확인() {
        // given
        long reviewId = 1L;
        ReviewScrap scrap1 = ReviewScrap.builder().memberId(1L).reviewId(reviewId).build();
        ReviewScrap scrap2 = ReviewScrap.builder().memberId(2L).reviewId(reviewId).build();
        fakeReviewScrapRepository.save(scrap1);
        fakeReviewScrapRepository.save(scrap2);

        // when
        long count = fakeReviewScrapRepository.countByReview(reviewId);

        // then
        assertEquals(2, count);
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
}
