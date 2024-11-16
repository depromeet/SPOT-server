package org.depromeet.spot.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.LevelRepository;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.service.review.UpdateReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

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
public class ViewsCountServiceTest {

    @Autowired UpdateReviewService updateReviewService;

    @Autowired ReadReviewUsecase readReviewUsecase;

    @Autowired MemberRepository memberRepository;

    @Autowired LevelRepository levelRepository;

    private static final int NUMBER_OF_THREAD = 100;

    @BeforeEach
    @Transactional
    void init() {
        Level level = levelRepository.findByValue(0);
        AtomicLong memberIdGenerator = new AtomicLong(1);

        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
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
    }

    @Test
    public void 멀티_스레드에서_조회수를_정상_카운트할_수_있다() throws InterruptedException {
        // given
        long reviewId = 1L;

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREAD);

        // when
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            executorService.execute(
                    () -> {
                        try {
                            updateReviewService.updateviewCount(reviewId);
                            System.out.println(
                                    "Thread " + Thread.currentThread().getId() + " - 성공");
                        } catch (Throwable e) {
                            System.out.println(
                                    "Thread "
                                            + Thread.currentThread().getId()
                                            + " - 실패"
                                            + e.getClass().getName());
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });
        }
        latch.await();
        executorService.shutdown();

        // then
        Review review = readReviewUsecase.findById(reviewId);
        System.out.println("스레드 : " + NUMBER_OF_THREAD);
        System.out.println("좋아요 카운트 : " + review.getViewsCount());
        assertEquals(NUMBER_OF_THREAD, review.getViewsCount());
    }
}
