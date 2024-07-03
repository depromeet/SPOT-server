package org.depromeet.spot.usecase.port.out;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);
}
