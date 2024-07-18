package org.depromeet.spot.usecase.port.out.member;

import java.util.Optional;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);

    Member update(Member member);

    Optional<Member> findByIdToken(String idToken);

    Boolean existsByNickname(String nickname);

    Member findById(Long memberId);
}
