package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;

public interface ReadMemberUsecase {

    Member findById(Long memberId);
}
