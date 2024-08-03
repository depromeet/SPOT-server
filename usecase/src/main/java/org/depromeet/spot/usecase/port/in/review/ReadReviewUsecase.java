package org.depromeet.spot.usecase.port.in.review;

import java.util.List;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.image.TopReviewImage;
import org.springframework.data.domain.Pageable;

import lombok.Builder;

public interface ReadReviewUsecase {

    BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable);

    MyReviewListResult findMyReviewsByUserId(
            Long userId, Integer year, Integer month, Pageable pageable);

    List<ReviewYearMonth> findReviewMonths(Long memberId);

    MyRecentReviewResult findLastReviewByMemberId(Long memberId);

    ReadReviewResult findReviewById(Long reviewId);

    long countByIdByMemberId(Long memberId);

    @Builder
    record BlockReviewListResult(
            LocationInfo location,
            List<Review> reviews,
            List<BlockKeywordInfo> topKeywords,
            List<TopReviewImage> topReviewImages,
            long totalElements,
            int totalPages,
            int number,
            int size) {}

    @Builder
    record BlockKeywordInfo(String content, Long count, Boolean isPositive) {}

    @Builder
    record LocationInfo(String stadiumName, String sectionName, String blockCode) {}

    @Builder
    record MyReviewListResult(
            MemberInfoOnMyReviewResult memberInfoOnMyReviewResult,
            List<Review> reviews,
            long totalElements,
            int totalPages,
            int number,
            int size) {}

    @Builder
    record MemberInfoOnMyReviewResult(
            Long userId,
            String profileImageUrl,
            Integer level,
            String levelTitle,
            String nickname,
            Long reviewCount,
            Long teamId,
            String teamName) {
        public static MemberInfoOnMyReviewResult of(Member member, long totalReviewCount) {
            return MemberInfoOnMyReviewResult.builder()
                    .userId(member.getId())
                    .profileImageUrl(member.getProfileImage())
                    .level(member.getLevel().getValue())
                    .levelTitle(member.getLevel().getTitle())
                    .nickname(member.getNickname())
                    .reviewCount(totalReviewCount)
                    .teamId(null)
                    .teamName(null)
                    .build();
        }

        public static MemberInfoOnMyReviewResult of(
                Member member, long totalReviewCount, String teamName) {
            return MemberInfoOnMyReviewResult.builder()
                    .userId(member.getId())
                    .profileImageUrl(member.getProfileImage())
                    .level(member.getLevel().getValue())
                    .levelTitle(member.getLevel().getTitle())
                    .nickname(member.getNickname())
                    .reviewCount(totalReviewCount)
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
