package org.depromeet.spot.usecase.service;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.MemberUsecase;
import org.depromeet.spot.usecase.port.out.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUsecase {

    private final MemberRepository memberRepository;

    @Override
    public Member create(final String name) {
        val member = new Member(null, name);
        return memberRepository.save(member);
    }
}
