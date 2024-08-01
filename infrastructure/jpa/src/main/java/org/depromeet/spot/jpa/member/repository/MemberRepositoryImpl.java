package org.depromeet.spot.jpa.member.repository;

import java.time.LocalDateTime;

import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.jpa.member.entity.MemberEntity;
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
        memberJpaRepository.updateLevel(member.getId(), member.getLevel().getId());
        return member;
    }

    @Override
    public Member findByIdToken(String idToken) {
        return memberJpaRepository
                .findByIdToken(idToken)
                .map(MemberEntity::toDomain)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    public Member findById(Long memberId) {
        MemberEntity entity =
                memberJpaRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
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
