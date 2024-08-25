package org.depromeet.spot.usecase.service.fake;

import java.time.LocalDateTime;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;

// FIXME: 구현 필요
public class FakeMemberRepository implements MemberRepository {

    @Override
    public Member save(Member member, Level level) {
        return null;
    }

    @Override
    public Member updateProfile(Member member) {
        return null;
    }

    @Override
    public Member updateLevel(Member member) {
        return null;
    }

    @Override
    public Member findByIdToken(String idToken) {
        return null;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return false;
    }

    @Override
    public Member findById(Long memberId) {
        return null;
    }

    @Override
    public void deleteByIdToken(String idToken) {}

    @Override
    public void updateDeletedAt(Long memberId, LocalDateTime deletedAt) {}

    @Override
    public void updateDeletedAtAndUpdatedAt(Long memberId, LocalDateTime updatedAt) {}
}
