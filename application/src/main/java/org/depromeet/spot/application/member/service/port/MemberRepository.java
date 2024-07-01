package org.depromeet.spot.application.member.service.port;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);
}
