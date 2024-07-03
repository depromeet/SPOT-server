package org.depromeet.spot.usecase.port.in;

import org.depromeet.spot.domain.member.Member;

public interface MemberUsecase {

    Member create(String name);
}
