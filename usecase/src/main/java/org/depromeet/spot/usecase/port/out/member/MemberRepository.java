package org.depromeet.spot.usecase.port.out.member;

import java.time.LocalDateTime;

import org.depromeet.spot.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);

    Member updateProfile(Member member);

    Member updateLevel(Member member);

    Member findByIdToken(String idToken);

    Boolean existsByNickname(String nickname);

    Member findById(Long memberId);

    void deleteByIdToken(String idToken);

    void updateDeletedAt(Long memberId, LocalDateTime deletedAt);
}
