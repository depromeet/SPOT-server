package org.depromeet.spot.usecase.port.in;

import java.util.List;

import org.depromeet.spot.domain.member.Member;

public interface MemberUsecase {

    Member create(String name);

    List<Member> findByName(String name);
}
