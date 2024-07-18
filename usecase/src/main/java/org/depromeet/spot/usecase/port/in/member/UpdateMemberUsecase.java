package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;

import lombok.Builder;

public interface UpdateMemberUsecase {

    Member updateProfile(Long memberId, UpdateProfileCommand command);

    @Builder
    record UpdateProfileCommand(String profileImage, String nickname, Long teamId) {}
}
