package org.depromeet.spot.jpa.member.repository.port;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);
}
