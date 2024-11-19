package org.depromeet.spot.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.out.member.LevelRepository;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.service.review.like.ReviewLikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlGroup({
    @Sql(
            value = "/sql/delete-data-after-review-like.sql",
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
    @Sql(
            value = "/sql/review-like-service-data.sql",
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
})
class ReviewLikeServiceTest {

    @Autowired private ReviewLikeService reviewLikeService;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private LevelRepository levelRepository;
    @Autowired private TransactionTemplate transactionTemplate;

    private static final int NUMBER_OF_THREADS = 100;

    @BeforeEach
    void init() {
        transactionTemplate.execute(
                status -> {
                    Level level = levelRepository.findByValue(0);
                    AtomicLong memberIdGenerator = new AtomicLong(1);

                    for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                        long memberId = memberIdGenerator.getAndIncrement();
                        memberRepository.save(
                                Member.builder()
                                        .id(memberId)
                                        .snsProvider(SnsProvider.KAKAO)
                                        .teamId(1L)
                                        .role(MemberRole.ROLE_ADMIN)
                                        .idToken("idToken" + memberId)
                                        .nickname(String.valueOf(memberId))
                                        .phoneNumber(String.valueOf(memberId))
                                        .email("email" + memberId)
                                        .build(),
                                level);
                    }
                    return null;
                });
    }

    @Test
    void 멀티_스레드_환경에서_리뷰_공감_수를_정상적으로_증가시킬_수_있다() throws InterruptedException {
        // given
        final long reviewId = 1L;
        AtomicLong memberIdGenerator = new AtomicLong(1);
        final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        final CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
        final AtomicInteger retryCount = new AtomicInteger(0);
        final AtomicInteger successCount = new AtomicInteger(0);
        final AtomicInteger failCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            long memberId = memberIdGenerator.getAndIncrement();
            executorService.execute(
                    () -> {
                        try {
                            reviewLikeService.toggleLike(memberId, reviewId);
                            successCount.incrementAndGet();
                        } catch (ObjectOptimisticLockingFailureException e) {
                            retryCount.incrementAndGet();
                        } catch (ConcurrentModificationException e) {
                            failCount.incrementAndGet();
                        } catch (Exception e) {
                            failCount.incrementAndGet();
                        } finally {
                            latch.countDown();
                        }
                    });
        }
        latch.await();
        executorService.shutdown();

        // then
        Review review =
                transactionTemplate.execute(
                        status -> {
                            return reviewRepository.findReviewByIdWithLock(reviewId);
                        });

        assertEquals(successCount.get(), review.getLikesCount(), "좋아요 수가 성공한 요청 수와 일치해야 함");
    }
}
