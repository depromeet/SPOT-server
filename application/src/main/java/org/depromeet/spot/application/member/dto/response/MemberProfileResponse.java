package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.domain.member.Member;

public record MemberProfileResponse(Long id, String nickname, Long teamId) {

    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(member.getId(), member.getNickname(), member.getTeamId());
    }
}
