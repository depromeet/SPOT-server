package org.depromeet.spot.usecase.service.review;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.BlockReviewListResult;
import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.MyReviewListResult;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.review.ReviewReadUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewReadService implements ReviewReadUsecase {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;

    private static final int TOP_KEYWORDS_LIMIT = 5;

    @Override
    public BlockReviewListResult findReviewsByBlockId(
            Long stadiumId,
            String blockCode,
            Long rowNumber,
            Long seatNumber,
            int offset,
            int limit) {
        List<Review> reviews =
                reviewRepository.findByBlockCode(
                        stadiumId, blockCode, rowNumber, seatNumber, offset, limit);
        Long totalCount =
                reviewRepository.countByBlockCode(stadiumId, blockCode, rowNumber, seatNumber);
        List<KeywordCount> topKeywords =
                reviewRepository.findTopKeywordsByBlockCode(
                        stadiumId, blockCode, TOP_KEYWORDS_LIMIT);

        Map<Long, Member> memberMap = getMemberMapForReviews(reviews);
        Map<Long, Seat> seatMap = getSeatMapForReviews(reviews);

        return new BlockReviewListResult(
                reviews, topKeywords, totalCount, offset, limit, memberMap, seatMap);
    }

    @Override
    public MyReviewListResult findMyReviews(
            Long userId, int offset, int limit, Integer year, Integer month) {
        List<Review> reviews = reviewRepository.findByUserId(userId, offset, limit, year, month);
        Long totalCount = reviewRepository.countByUserId(userId, year, month);

        Map<Long, Member> memberMap = getMemberMapForReviews(reviews);
        Map<Long, Seat> seatMap = getSeatMapForReviews(reviews);

        return new MyReviewListResult(reviews, totalCount, offset, limit, memberMap, seatMap);
    }

    @Override
    public List<ReviewYearMonth> findReviewMonths(Long memberId) {
        return reviewRepository.findReviewMonthsByMemberId(memberId);
    }

    private Map<Long, Member> getMemberMapForReviews(List<Review> reviews) {
        List<Long> memberIds =
                reviews.stream().map(Review::getUserId).distinct().collect(Collectors.toList());
        List<Member> members = memberRepository.findAllById(memberIds);
        return members.stream().collect(Collectors.toMap(Member::getId, member -> member));
    }

    private Map<Long, Seat> getSeatMapForReviews(List<Review> reviews) {
        List<Long> seatIds =
                reviews.stream().map(Review::getSeatId).distinct().collect(Collectors.toList());
        List<Seat> seats = seatRepository.findAllById(seatIds);
        return seats.stream().collect(Collectors.toMap(Seat::getId, seat -> seat));
    }
}
