package org.depromeet.spot.jpa.member.repository;

import java.util.List;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.jpa.member.entity.MemberEntity;
import org.depromeet.spot.usecase.port.out.MemberRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberCustomRepository memberCustomRepository;

    @Override
    public Member save(Member member) {
        val memberEntity = memberJpaRepository.save(MemberEntity.from(member));
        return memberEntity.toDomain();
    }

    @Override
    public List<Member> findByName(String name) {
        val memberEntities = memberCustomRepository.findByName(name);
        return memberEntities.stream().map(MemberEntity::toDomain).toList();
    }
}