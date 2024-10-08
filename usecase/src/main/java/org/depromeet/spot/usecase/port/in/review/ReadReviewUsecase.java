package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewCount;
import org.depromeet.spot.domain.review.ReviewYearMonth;

import lombok.Builder;

public interface ReadReviewUsecase {

    BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long memberId,
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size);

    MyReviewListResult findMyReviewsByUserId(
            Long userId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size,
            ReviewType reviewType);

    MemberInfoOnMyReviewResult findMemberInfoOnMyReview(Long memberId);

    List<ReviewYearMonth> findReviewMonths(Long memberId, ReviewType reviewType);

    MyRecentReviewResult findLastReviewByMemberId(Long memberId);

    ReadReviewResult findReviewById(Long reviewId, Long memberId);

    long countByIdByMemberId(Long memberId);

    long countByMember(Long memberId);

    Review findById(long reviewId);

    @Builder
    record BlockReviewListResult(
            LocationInfo location,
            List<Review> reviews,
            List<BlockKeywordInfo> topKeywords,
            List<Review> topReviewImages,
            Long totalElements,
            String nextCursor,
            boolean hasNext) {}

    @Builder
    record BlockKeywordInfo(String content, Long count, Boolean isPositive) {}

    @Builder
    record LocationInfo(String stadiumName, String sectionName, String blockCode) {}

    @Builder
    record MyReviewListResult(
            MemberInfoOnMyReviewResult memberInfoOnMyReviewResult,
            List<Review> reviews,
            String nextCursor,
            boolean hasNext) {}

    @Builder
    record MemberInfoOnMyReviewResult(
            Long userId,
            String profileImageUrl,
            Integer level,
            String levelTitle,
            String nickname,
            Long reviewCount,
            Long totalLikes,
            Long teamId,
            String teamName) {
        public static MemberInfoOnMyReviewResult of(Member member, ReviewCount reviewCount) {
            return MemberInfoOnMyReviewResult.builder()
                    .userId(member.getId())
                    .profileImageUrl(member.getProfileImage())
                    .level(member.getLevel().getValue())
                    .levelTitle(member.getLevel().getTitle())
                    .nickname(member.getNickname())
                    .reviewCount(reviewCount.reviewCount())
                    .totalLikes(reviewCount.totalLikes())
                    .teamId(null)
                    .teamName(null)
                    .build();
        }

        public static MemberInfoOnMyReviewResult of(
                Member member, ReviewCount reviewCount, String teamName) {
            return MemberInfoOnMyReviewResult.builder()
                    .userId(member.getId())
                    .profileImageUrl(member.getProfileImage())
                    .level(member.getLevel().getValue())
                    .levelTitle(member.getLevel().getTitle())
                    .nickname(member.getNickname())
                    .reviewCount(reviewCount.reviewCount())
                    .totalLikes(reviewCount.totalLikes())
                    .teamId(member.getTeamId())
                    .teamName(teamName)
                    .build();
        }
    }

    @Builder
    record MyRecentReviewResult(Review review, Long reviewCount) {}

    @Builder
    record ReadReviewResult(Review review) {}
}
