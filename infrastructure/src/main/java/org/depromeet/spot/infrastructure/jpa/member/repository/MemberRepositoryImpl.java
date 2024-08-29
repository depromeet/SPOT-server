package org.depromeet.spot.infrastructure.jpa.member.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.infrastructure.jpa.member.entity.MemberEntity;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member, Level level) {
        MemberEntity memberEntity = memberJpaRepository.save(MemberEntity.of(member, level));
        return memberEntity.toDomain();
    }

    @Override
    public Member updateProfile(Member member) {
        memberJpaRepository.updateProfile(
                member.getId(), member.getProfileImage(), member.getTeamId(), member.getNickname());
        return member;
    }

    @Override
    public Member updateLevel(Member member) {
        MemberEntity memberEntity = memberJpaRepository.save(MemberEntity.withMember(member));
        return memberEntity.toDomain();
    }

    @Override
    public Optional<Member> findByIdToken(String idToken) {
        return memberJpaRepository.findByIdToken(idToken).map(MemberEntity::toDomain);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    public Member findById(Long memberId) {
        MemberEntity entity =
                memberJpaRepository
                        .findByIdWithLevel(memberId)
                        .orElseThrow(MemberNotFoundException::new);
        return entity.toDomain();
    }

    @Override
    public void deleteByIdToken(String idToken) {
        memberJpaRepository.deleteByIdToken(idToken);
    }

    @Override
    public void updateDeletedAt(Long memberId, LocalDateTime deletedAt) {
        memberJpaRepository.updateDeletedAt(memberId, deletedAt);
    }

    @Override
    public void updateDeletedAtAndUpdatedAt(Long memberId, LocalDateTime updatedAt) {
        memberJpaRepository.updateDeletedAtAndUpdatedAt(memberId, updatedAt);
    }
}
