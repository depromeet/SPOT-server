package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;

import lombok.Builder;

public interface UpdateMemberUsecase {

    Member updateProfile(Long memberId, UpdateProfileCommand command);

    void updateLevel(Long memberId, long reviewCnt);

    @Builder
    record UpdateProfileCommand(String profileImage, String nickname, Long teamId) {}
}
