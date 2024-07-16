package org.depromeet.spot.usecase.port.out.member;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);

    Member findByIdToken(String idToken);

    Boolean existsByNickname(String nickname);
}
