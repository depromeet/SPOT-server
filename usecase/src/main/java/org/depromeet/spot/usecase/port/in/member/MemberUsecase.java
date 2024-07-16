package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Member;

public interface MemberUsecase {

    Member create(Member member);

    Member login(String idCode);

    Boolean duplicatedNickname(String nickname);
}
