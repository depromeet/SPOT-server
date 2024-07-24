package org.depromeet.spot.usecase.port.out.member;

import java.util.Optional;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);

    Member updateProfile(Member member);

    Member updateLevel(Member member);

    Optional<Member> findByIdToken(String idToken);

    boolean existsByNickname(String nickname);

    Member findById(Long memberId);

    void deleteByIdToken(String idToken);
}
