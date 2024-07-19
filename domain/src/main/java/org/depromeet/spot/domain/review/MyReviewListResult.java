package org.depromeet.spot.domain.review;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.seat.Seat;

public record MyReviewListResult(
        List<Review> reviews,
        long totalCount,
        int offset,
        int limit,
        Map<Long, Member> memberMap,
        Map<Long, Seat> seatMap) {
    public Member getMemberByReviewId(Long reviewId) {
        return memberMap.get(reviewId);
    }

    public Seat getSeatByReviewId(Long reviewId) {
        return seatMap.get(reviewId);
    }
}
