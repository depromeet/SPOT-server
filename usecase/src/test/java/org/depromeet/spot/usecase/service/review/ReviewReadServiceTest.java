// package org.depromeet.spot.usecase.service.review;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.List;
//
// import org.depromeet.spot.domain.block.Block;
// import org.depromeet.spot.domain.block.BlockRow;
// import org.depromeet.spot.domain.member.Member;
// import org.depromeet.spot.domain.review.Review;
// import org.depromeet.spot.domain.review.ReviewYearMonth;
// import org.depromeet.spot.domain.review.image.ReviewImage;
// import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
// import org.depromeet.spot.domain.review.result.BlockReviewListResult;
// import org.depromeet.spot.domain.review.result.MyReviewListResult;
// import org.depromeet.spot.domain.seat.Seat;
// import org.depromeet.spot.domain.section.Section;
// import org.depromeet.spot.domain.stadium.Stadium;
// import org.depromeet.spot.usecase.service.fake.FakeReviewImageRepository;
// import org.depromeet.spot.usecase.service.fake.FakeReviewKeywordRepository;
// import org.depromeet.spot.usecase.service.fake.FakeReviewRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// class ReviewReadServiceTest {
//
//    private ReviewReadService reviewReadService;
//    private FakeReviewRepository fakeReviewRepository;
//    private FakeReviewImageRepository fakeReviewImageRepository;
//    private FakeReviewKeywordRepository fakeReviewKeywordRepository;
//
//    @BeforeEach
//    void setUp() {
//        fakeReviewRepository = new FakeReviewRepository();
//        fakeReviewImageRepository = new FakeReviewImageRepository();
//        fakeReviewKeywordRepository = new FakeReviewKeywordRepository();
//        reviewReadService =
//                new ReviewReadService(
//                        fakeReviewRepository,
//                        fakeReviewKeywordRepository,
//                        fakeReviewImageRepository);
//    }
//
//    @Test
//    void findReviewsByStadiumIdAndBlockCode는_블록별_리뷰를_정상적으로_반환한다() {
//        // Given
//        Stadium stadium = Stadium.builder().id(1L).name("Test Stadium").build();
//        Section section = Section.builder().id(1L).name("Test Section").build();
//        Block block = Block.builder().id(1L).code("A").build();
//        BlockRow row = BlockRow.builder().id(1L).number(1).build();
//        Seat seat = Seat.builder().id(1L).seatNumber(1).build();
//        Member member = Member.builder().id(1L).nickname("Test User").build();
//
//        Review review1 =
//                Review.builder()
//                        .id(1L)
//                        .member(member)
//                        .stadium(stadium)
//                        .section(section)
//                        .block(block)
//                        .row(row)
//                        .seat(seat)
//                        .content("Great game!")
//                        .dateTime(LocalDateTime.now())
//                        .build();
//
//        Review review2 =
//                Review.builder()
//                        .id(2L)
//                        .member(member)
//                        .stadium(stadium)
//                        .section(section)
//                        .block(block)
//                        .row(row)
//                        .seat(Seat.builder().id(2L).seatNumber(2).build())
//                        .content("Amazing view!")
//                        .dateTime(LocalDateTime.now())
//                        .build();
//
//        fakeReviewRepository.save(review1);
//        fakeReviewRepository.save(review2);
//
//        ReviewKeyword keyword1 =
//                ReviewKeyword.builder()
//                        .id(1L)
//                        .review(review1)
//                        .content("Good")
//                        .isPositive(true)
//                        .build();
//        ReviewKeyword keyword2 =
//                ReviewKeyword.builder()
//                        .id(2L)
//                        .review(review2)
//                        .content("Bad")
//                        .isPositive(false)
//                        .build();
//        fakeReviewKeywordRepository.saveAll(Arrays.asList(keyword1, keyword2));
//
//        ReviewImage image1 =
// ReviewImage.builder().id(1L).review(review1).url("image1.jpg").build();
//        ReviewImage image2 =
// ReviewImage.builder().id(2L).review(review2).url("image2.jpg").build();
//        fakeReviewImageRepository.saveAll(Arrays.asList(image1, image2));
//
//        // When
//        BlockReviewListResult result =
//                reviewReadService.findReviewsByStadiumIdAndBlockCode(
//                        1L, "A", null, null, null, null, 0, 10);
//
//        // Then
//        assertThat(result.getReviews()).hasSize(2);
//        assertThat(result.getTotalElements()).isEqualTo(2);
//        assertThat(result.getTopKeywords()).hasSize(2);
//        assertThat(result.getTopReviewImages()).hasSize(2);
//    }
//
//    @Test
//    void findReviewsByStadiumIdAndBlockCode는_조회된_리뷰가_없을_시_빈_결과를_반환한다() {
//        // When
//        BlockReviewListResult result =
//                reviewReadService.findReviewsByStadiumIdAndBlockCode(
//                        999L, "Z", null, null, null, null, 0, 10);
//
//        // Then
//        assertThat(result.getReviews()).isEmpty();
//        assertThat(result.getTotalElements()).isZero();
//    }
//
//    @Test
//    void findReviewsByStadiumIdAndBlockCode는_페이징을_정상적으로_처리한다() {
//        // Given
//        Stadium stadium = Stadium.builder().id(1L).name("Test Stadium").build();
//        Section section = Section.builder().id(1L).name("Test Section").build();
//        Block block = Block.builder().id(1L).code("A").build();
//        BlockRow row = BlockRow.builder().id(1L).number(1).build();
//        Member member = Member.builder().id(1L).nickname("Test User").build();
//
//        for (int i = 0; i < 20; i++) {
//            Review review =
//                    Review.builder()
//                            .id((long) i + 1)
//                            .member(member)
//                            .stadium(stadium)
//                            .section(section)
//                            .block(block)
//                            .row(row)
//                            .seat(Seat.builder().id((long) i + 1).seatNumber(i + 1).build())
//                            .content("Review " + i)
//                            .dateTime(LocalDateTime.now())
//                            .build();
//            fakeReviewRepository.save(review);
//        }
//
//        // When
//        BlockReviewListResult result =
//                reviewReadService.findReviewsByStadiumIdAndBlockCode(
//                        1L, "A", null, null, null, null, 1, 10);
//
//        // Then
//        assertThat(result.getReviews()).hasSize(10);
//        assertThat(result.getTotalElements()).isEqualTo(20);
//        assertThat(result.getNumber()).isEqualTo(1);
//    }
//
//    @Test
//    void findMyReviewsByUserId는_유저별_리뷰를_정상적으로_반환한다() {
//        // Given
//        Member member = Member.builder().id(1L).nickname("Test User").build();
//        Stadium stadium = Stadium.builder().id(1L).name("Test Stadium").build();
//        Section section = Section.builder().id(1L).name("Test Section").build();
//        Block block = Block.builder().id(1L).code("A").build();
//        BlockRow row = BlockRow.builder().id(1L).number(1).build();
//        Seat seat = Seat.builder().id(1L).seatNumber(1).build();
//
//        Review review1 =
//                Review.builder()
//                        .id(1L)
//                        .member(member)
//                        .stadium(stadium)
//                        .section(section)
//                        .block(block)
//                        .row(row)
//                        .seat(seat)
//                        .content("Great game!")
//                        .dateTime(LocalDateTime.now())
//                        .build();
//
//        Review review2 =
//                Review.builder()
//                        .id(2L)
//                        .member(member)
//                        .stadium(stadium)
//                        .section(section)
//                        .block(block)
//                        .row(row)
//                        .seat(seat)
//                        .content("Amazing view!")
//                        .dateTime(LocalDateTime.now())
//                        .build();
//
//        fakeReviewRepository.save(review1);
//        fakeReviewRepository.save(review2);
//
//        // When
//        MyReviewListResult result = reviewReadService.findMyReviewsByUserId(1L, null, null, 0,
// 10);
//
//        // Then
//        assertThat(result.getReviews()).hasSize(2);
//        assertThat(result.getTotalElements()).isEqualTo(2);
//    }
//
//    @Test
//    void findReviewMonths는_사용자의_리뷰_작성_월을_정상적으로_반환한다() {
//        // Given
//        Member member = Member.builder().id(1L).nickname("Test User").build();
//        Stadium stadium = Stadium.builder().id(1L).name("Test Stadium").build();
//        Section section = Section.builder().id(1L).name("Test Section").build();
//        Block block = Block.builder().id(1L).code("A").build();
//        BlockRow row = BlockRow.builder().id(1L).number(1).build();
//        Seat seat = Seat.builder().id(1L).seatNumber(1).build();
//
//        Review review1 =
//                Review.builder()
//                        .id(1L)
//                        .member(member)
//                        .stadium(stadium)
//                        .section(section)
//                        .block(block)
//                        .row(row)
//                        .seat(seat)
//                        .content("January Review")
//                        .dateTime(LocalDateTime.of(2023, 1, 1, 0, 0))
//                        .build();
//
//        Review review2 =
//                Review.builder()
//                        .id(2L)
//                        .member(member)
//                        .stadium(stadium)
//                        .section(section)
//                        .block(block)
//                        .row(row)
//                        .seat(seat)
//                        .content("March Review")
//                        .dateTime(LocalDateTime.of(2023, 3, 1, 0, 0))
//                        .build();
//
//        fakeReviewRepository.save(review1);
//        fakeReviewRepository.save(review2);
//
//        // When
//        List<ReviewYearMonth> result = reviewReadService.findReviewMonths(1L);
//
//        // Then
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).year()).isEqualTo(2023);
//        assertThat(result.get(0).month()).isEqualTo(3);
//        assertThat(result.get(1).year()).isEqualTo(2023);
//        assertThat(result.get(1).month()).isEqualTo(1);
//    }
// }
