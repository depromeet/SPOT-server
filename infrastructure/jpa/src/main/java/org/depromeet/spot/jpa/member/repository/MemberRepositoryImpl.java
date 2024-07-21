package org.depromeet.spot.jpa.member.repository;

import java.util.Optional;

import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
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
    public Member save(Member member) {
        MemberEntity memberEntity = memberJpaRepository.save(MemberEntity.from(member));
        return memberEntity.toDomain();
    }

    @Override
    public Member update(Member member) {
        memberJpaRepository.updateProfile(
                member.getId(), member.getProfileImage(), member.getTeamId(), member.getNickname());
        return member;
    }

    @Override
    public Optional<Member> findByIdToken(String idToken) {
        return memberJpaRepository.findByIdToken(idToken).map(MemberEntity::toDomain);
    }

    @Override
    public Boolean existsByNickname(String nickname) {
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
}
