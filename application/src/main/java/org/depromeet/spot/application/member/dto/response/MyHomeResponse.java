package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.usecase.port.in.member.MemberUsecase.MemberInfo;

import lombok.Builder;

@Builder
public record MyHomeResponse(
        String profileImageUrl,
        String nickname,
        Integer level,
        String levelTitle,
        String teamImageUrl) {

    public static MyHomeResponse from(MemberInfo memberInfo) {
        return MyHomeResponse.builder()
                .profileImageUrl(memberInfo.getProfileImageUrl())
                .nickname(memberInfo.getNickname())
                .level(memberInfo.getLevel())
                .levelTitle(memberInfo.getLevelTitle())
                .teamImageUrl(memberInfo.getTeamImageUrl())
                .build();
    }
}
