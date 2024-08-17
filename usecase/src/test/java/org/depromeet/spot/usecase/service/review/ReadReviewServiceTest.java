package org.depromeet.spot.usecase.service.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReadReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private ReviewImageRepository reviewImageRepository;
    @Mock private BlockTopKeywordRepository blockTopKeywordRepository;
    @Mock private KeywordRepository keywordRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private BaseballTeamRepository baseballTeamRepository;

    private ReadReviewService readReviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readReviewService =
                new ReadReviewService(
                        reviewRepository,
                        reviewImageRepository,
                        blockTopKeywordRepository,
                        keywordRepository,
                        memberRepository,
                        baseballTeamRepository);
    }

    @Test
    void findReviewsByStadiumIdAndBlockCode_리뷰를_정상적으로_반환한다() {
        // Given
        Long stadiumId = 1L;
        String blockCode = "A";
        when(reviewRepository.findLocationInfoByStadiumIdAndBlockCode(stadiumId, blockCode))
                .thenReturn(new ReadReviewUsecase.LocationInfo("Stadium", "Section", "A"));

        List<Review> mockReviews = Arrays.asList(createMockReview(1L), createMockReview(2L));
        when(reviewRepository.findByStadiumIdAndBlockCode(
                        eq(stadiumId), eq(blockCode), any(), any(), any(), any(), any(), any()))
                .thenReturn(mockReviews);

        when(blockTopKeywordRepository.findTopKeywordsByStadiumIdAndBlockCode(
                        eq(stadiumId), eq(blockCode), anyInt()))
                .thenReturn(Collections.emptyList());

        when(reviewImageRepository.findTopReviewImagesByStadiumIdAndBlockCode(
                        eq(stadiumId), eq(blockCode), anyInt()))
                .thenReturn(Collections.emptyList());

        when(keywordRepository.findByIds(anyList())).thenReturn(Collections.emptyMap());

        // When
        ReadReviewUsecase.BlockReviewListResult result =
                readReviewService.findReviewsByStadiumIdAndBlockCode(
                        stadiumId, blockCode, null, null, null, null, 0L, 10);

        // Then
        assertThat(result.reviews()).hasSize(2);
        assertThat(result.location().stadiumName()).isEqualTo("Stadium");
        assertThat(result.location().sectionName()).isEqualTo("Section");
        assertThat(result.location().blockCode()).isEqualTo("A");
    }

    @Test
    void findMyReviewsByUserId_사용자의_리뷰를_정상적으로_반환한다() {
        // Given
        Long userId = 1L;
        List<Review> mockReviews = Arrays.asList(createMockReview(1L), createMockReview(2L));
        when(reviewRepository.findAllByUserId(eq(userId), any(), any(), any(), any()))
                .thenReturn(mockReviews);

        when(keywordRepository.findByIds(anyList())).thenReturn(Collections.emptyMap());

        when(memberRepository.findById(userId)).thenReturn(createMockMember(userId));
        when(reviewRepository.countByUserId(userId)).thenReturn(2L);

        // When
        ReadReviewUsecase.MyReviewListResult result =
                readReviewService.findMyReviewsByUserId(userId, null, null, 0L, 10);

        // Then
        assertThat(result.reviews()).hasSize(2);
        assertThat(result.memberInfoOnMyReviewResult().nickname()).isEqualTo("Test User");
        assertThat(result.memberInfoOnMyReviewResult().reviewCount()).isEqualTo(2);
    }

    @Test
    void findReviewMonths_사용자의_리뷰_작성_월을_정상적으로_반환한다() {
        // Given
        Long memberId = 1L;
        List<ReviewYearMonth> mockMonths =
                Arrays.asList(new ReviewYearMonth(2023, 3), new ReviewYearMonth(2023, 1));
        when(reviewRepository.findReviewMonthsByMemberId(memberId)).thenReturn(mockMonths);

        // When
        List<ReviewYearMonth> result = readReviewService.findReviewMonths(memberId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).year()).isEqualTo(2023);
        assertThat(result.get(0).month()).isEqualTo(3);
        assertThat(result.get(1).year()).isEqualTo(2023);
        assertThat(result.get(1).month()).isEqualTo(1);
    }

    @Test
    void findReviewById_리뷰를_정상적으로_반환한다() {
        // Given
        Long reviewId = 1L;
        Review mockReview = createMockReview(reviewId);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
        when(keywordRepository.findByIds(anyList())).thenReturn(Collections.emptyMap());

        // When
        ReadReviewUsecase.ReadReviewResult result = readReviewService.findReviewById(reviewId);

        // Then
        assertThat(result.review().getId()).isEqualTo(reviewId);
    }

    @Test
    void findLastReviewByMemberId_마지막_리뷰를_정상적으로_반환한다() {
        // Given
        Long memberId = 1L;
        Review mockReview = createMockReview(1L);
        when(reviewRepository.findLastReviewByMemberId(memberId)).thenReturn(mockReview);
        when(reviewRepository.countByIdByMemberId(memberId)).thenReturn(5L);
        when(keywordRepository.findByIds(anyList())).thenReturn(Collections.emptyMap());

        // When
        ReadReviewUsecase.MyRecentReviewResult result =
                readReviewService.findLastReviewByMemberId(memberId);

        // Then
        assertThat(result.review().getId()).isEqualTo(1L);
        assertThat(result.reviewCount()).isEqualTo(5L);
    }

    private Review createMockReview(Long id) {
        return Review.builder()
                .id(id)
                .member(createMockMember(1L))
                .stadium(Stadium.builder().id(1L).name("Test Stadium").build())
                .section(Section.builder().id(1L).name("Test Section").build())
                .block(Block.builder().id(1L).code("A").build())
                .row(BlockRow.builder().id(1L).number(1).build())
                .seat(Seat.builder().id(1L).seatNumber(1).build())
                .dateTime(LocalDateTime.now())
                .content("Test content")
                .keywords(Collections.singletonList(new ReviewKeyword(1L, 1L)))
                .images(new ArrayList<>())
                .build();
    }

    private Member createMockMember(Long id) {
        return Member.builder().id(id).nickname("Test User").build();
    }
}
