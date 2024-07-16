package org.depromeet.spot.jpa.member.repository;

import lombok.RequiredArgsConstructor;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.jpa.member.entity.MemberEntity;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.springframework.stereotype.Repository;

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
    public Member findByIdToken(String idToken){
        return memberJpaRepository.findByIdToken(idToken).toDomain();
    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }


}
