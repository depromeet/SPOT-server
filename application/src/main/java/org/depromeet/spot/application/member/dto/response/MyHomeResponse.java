package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.usecase.port.in.member.MemberUsecase.MemberInfo;

import lombok.Builder;

@Builder
public record MyHomeResponse(
        String profileImageUrl, String nickname, Integer level, String teamImageUrl) {
    public static MyHomeResponse from(MemberInfo memberInfo) {
        return MyHomeResponse.builder()
                .profileImageUrl(memberInfo.getMember().getProfileImage())
                .nickname(memberInfo.getMember().getNickname())
                .level(memberInfo.getMember().getLevel())
                .teamImageUrl(memberInfo.getBaseballTeam().getLogo())
                .build();
    }
}
