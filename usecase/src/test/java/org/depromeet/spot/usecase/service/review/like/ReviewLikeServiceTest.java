package org.depromeet.spot.usecase.service.review.like;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.service.fake.FakeBaseballTeamRepository;
import org.depromeet.spot.usecase.service.fake.FakeBlockTopKeywordRepository;
import org.depromeet.spot.usecase.service.fake.FakeKeywordRepository;
import org.depromeet.spot.usecase.service.fake.FakeMemberRepository;
import org.depromeet.spot.usecase.service.fake.FakeReviewImageRepository;
import org.depromeet.spot.usecase.service.fake.FakeReviewLikeRepository;
import org.depromeet.spot.usecase.service.fake.FakeReviewRepositoryV2;
import org.depromeet.spot.usecase.service.fake.FakeSeatRepository;
import org.depromeet.spot.usecase.service.review.ReadReviewService;
import org.depromeet.spot.usecase.service.review.UpdateReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ReviewLikeServiceTest {

    private ReviewLikeService reviewLikeService;
    private ReadReviewService readReviewService;

    @BeforeEach
    void init() {
        FakeReviewLikeRepository reviewLikeRepository = new FakeReviewLikeRepository();
        FakeReviewRepositoryV2 reviewRepository = new FakeReviewRepositoryV2();
        reviewRepository.save(Review.builder().id(1L).likesCount(0).build());

        ReadReviewService readReviewService =
                ReadReviewService.builder()
                        .reviewRepository(reviewRepository)
                        .reviewImageRepository(new FakeReviewImageRepository())
                        .blockTopKeywordRepository(new FakeBlockTopKeywordRepository())
                        .keywordRepository(new FakeKeywordRepository())
                        .memberRepository(new FakeMemberRepository())
                        .baseballTeamRepository(new FakeBaseballTeamRepository())
                        .build();
        this.readReviewService = readReviewService;

        UpdateReviewService updateReviewService =
                UpdateReviewService.builder()
                        .reviewRepository(reviewRepository)
                        .seatRepository(new FakeSeatRepository())
                        .keywordRepository(new FakeKeywordRepository())
                        .blockTopKeywordRepository(new FakeBlockTopKeywordRepository())
                        .build();

        this.reviewLikeService =
                ReviewLikeService.builder()
                        .reviewLikeRepository(reviewLikeRepository)
                        .readReviewUsecase(readReviewService)
                        .updateReviewUsecase(updateReviewService)
                        .build();
    }

    @Test
    void 멀티_스레드_환경에서_리뷰_공감_수를_정상적으로_증가시킬_수_있다() throws InterruptedException {
        // given
        final long reviewId = 1;
        final int numberOfThread = 100;
        AtomicLong memberIdGenerator = new AtomicLong(1);
        final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThread);
        final CountDownLatch latch = new CountDownLatch(numberOfThread);

        // when
        for (int i = 0; i < numberOfThread; i++) {
            long memberId = memberIdGenerator.getAndIncrement();
            executorService.execute(
                    () -> {
                        try {
                            reviewLikeService.toggleLike(memberId, reviewId);
                            log.info("Thread " + Thread.currentThread().getId() + " - 성공");
                        } catch (Throwable e) {
                            log.info("Thread " + Thread.currentThread().getId() + " - 실패");
                        } finally {
                            latch.countDown();
                        }
                    });
        }
        latch.await();
        executorService.shutdown();

        // then
        Review review = readReviewService.findById(reviewId);
        assertEquals(100, review.getLikesCount());
    }
}
