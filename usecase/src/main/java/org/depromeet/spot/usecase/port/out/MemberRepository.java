package org.depromeet.spot.usecase.port.out;

import java.util.List;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);

    List<Member> findByName(String name);
}
