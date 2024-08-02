package org.depromeet.spot.application.member.dto.response;

import org.depromeet.spot.domain.member.Member;

public record MemberReviewProfileResponse(
        Long id, String nickname, Integer level, String profileImageUrl) {
    public static MemberReviewProfileResponse from(Member member) {
        return new MemberReviewProfileResponse(
                member.getId(),
                member.getNickname(),
                member.getLevel().getValue(),
                member.getProfileImage());
    }
}
