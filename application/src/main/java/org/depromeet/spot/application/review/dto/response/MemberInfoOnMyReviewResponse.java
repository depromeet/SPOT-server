package org.depromeet.spot.application.review.dto.response;

import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.MemberInfoOnMyReviewResult;

import lombok.Builder;

@Builder
public record MemberInfoOnMyReviewResponse(
        Long userId,
        String profileImageUrl,
        Integer level,
        String levelTitle,
        String nickname,
        Long reviewCount,
        Long totalLikes,
        Long teamId,
        String teamName) {
    public static MemberInfoOnMyReviewResponse from(MemberInfoOnMyReviewResult result) {
        return MemberInfoOnMyReviewResponse.builder()
                .userId(result.userId())
                .profileImageUrl(result.profileImageUrl())
                .level(result.level())
                .levelTitle(result.levelTitle())
                .nickname(result.nickname())
                .reviewCount(result.reviewCount())
                .totalLikes(result.totalLikes())
                .teamId(result.teamId())
                .teamName(result.teamName())
                .build();
    }
}
