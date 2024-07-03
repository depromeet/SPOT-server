package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.domain.member.Member;

public record MemberResponse(Long id, String name) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}
