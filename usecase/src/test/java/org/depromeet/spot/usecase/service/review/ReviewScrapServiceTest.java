package org.depromeet.spot.usecase.service.review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.page.PageCommand;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase.MyScrapCommand;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase.MyScrapListResult;
import org.depromeet.spot.usecase.port.out.mixpanel.MixpanelRepository;
import org.depromeet.spot.usecase.service.fake.FakeReviewScrapRepository;
import org.depromeet.spot.usecase.service.review.processor.PaginationProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReadReviewProcessor;
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
    @Mock private ReadReviewService readReviewService;
    @Mock private ReadReviewProcessor readReviewProcessor;
    @Mock private PaginationProcessor paginationProcessor;
    @Mock private MixpanelRepository mixpanelRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        fakeReviewScrapRepository = new FakeReviewScrapRepository();
        reviewScrapService =
                new ReviewScrapService(
                        readReviewUsecase,
                        updateReviewUsecase,
                        fakeReviewScrapRepository,
                        readReviewService,
                        readReviewProcessor,
                        paginationProcessor,
                        mixpanelRepository);
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

    @Test
    void 스크랩된_리뷰_조회() {
        // given
        long memberId = 1L;
        long stadiumId = 1L;
        Stadium stadium = Stadium.builder().id(stadiumId).name("Test Stadium").build();

        Keyword goodKeyword = new Keyword(1L, "좋아요", true);
        Keyword badKeyword = new Keyword(2L, "별로에요", false);
        fakeReviewScrapRepository.addKeyword(goodKeyword);
        fakeReviewScrapRepository.addKeyword(badKeyword);

        Review review1 =
                createReview(
                        1L,
                        stadium,
                        LocalDateTime.of(2023, 5, 1, 10, 0),
                        Arrays.asList(
                                new ReviewKeyword(1L, goodKeyword.getId()),
                                new ReviewKeyword(2L, badKeyword.getId())));
        Review review2 =
                createReview(
                        2L,
                        stadium,
                        LocalDateTime.of(2023, 6, 1, 10, 0),
                        Arrays.asList(new ReviewKeyword(3L, goodKeyword.getId())));

        fakeReviewScrapRepository.addReview(review1);
        fakeReviewScrapRepository.addReview(review2);
        fakeReviewScrapRepository.save(
                ReviewScrap.builder().memberId(memberId).reviewId(review1.getId()).build());
        fakeReviewScrapRepository.save(
                ReviewScrap.builder().memberId(memberId).reviewId(review2.getId()).build());

        MyScrapCommand command =
                new MyScrapCommand(
                        stadiumId,
                        Arrays.asList(5, 6),
                        Arrays.asList("좋아요"),
                        Arrays.asList("별로에요"));
        PageCommand pageCommand = new PageCommand(null, SortCriteria.DATE_TIME, 10);

        when(readReviewService.mapKeywordsToReviews(anyList()))
                .thenReturn(Arrays.asList(review2, review1));

        // when
        MyScrapListResult result =
                reviewScrapService.findMyScrappedReviews(memberId, command, pageCommand);

        // then
        assertEquals(2, result.reviews().size());
        assertEquals(review2.getId(), result.reviews().get(0).getId()); // 최신 리뷰가 먼저 오는지 확인
        assertEquals(review1.getId(), result.reviews().get(1).getId());
        //        assertEquals(2, result.totalScrapCount());
        assertFalse(result.hasNext());
        assertNull(result.nextCursor());
    }

    private Review createReview(
            Long id, Stadium stadium, LocalDateTime dateTime, List<ReviewKeyword> keywords) {
        return Review.builder()
                .id(id)
                .stadium(stadium)
                .dateTime(dateTime)
                .keywords(keywords)
                .build();
    }
}
