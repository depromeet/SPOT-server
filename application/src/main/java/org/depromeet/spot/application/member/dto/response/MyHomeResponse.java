package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.usecase.port.in.member.MemberUsecase.MemberInfo;

import lombok.Builder;

@Builder
public record MyHomeResponse(
        Integer level,
        String teamName,
        Long teamId,
        String levelTitle,
        Long reviewCntToLevelUp,
        String levelTitleUrl) {

    public static MyHomeResponse from(MemberInfo memberInfo) {
        return MyHomeResponse.builder()
                .level(memberInfo.getLevel())
                .teamName(memberInfo.getTeamName())
                .teamId(memberInfo.getTeamId())
                .levelTitle(memberInfo.getLevelTitle())
                .reviewCntToLevelUp(memberInfo.getReviewCntToLevelUp())
                .levelTitleUrl(memberInfo.getLevelTitleUrl())
                .build();
    }
}
