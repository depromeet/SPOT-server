package org.depromeet.spot.usecase.service;

import java.util.List;

import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.MemberUsecase;
import org.depromeet.spot.usecase.port.out.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUsecase {

    private final MemberRepository memberRepository;

    @Override
    public Member create(final String name) {
        var member = new Member(null, name);
        return memberRepository.save(member);
    }

    @Override
    public List<Member> findByName(final String name) {
        var members = memberRepository.findByName(name);
        if (members.isEmpty()) {
            throw new MemberNotFoundException(name);
        }
        return members;
    }
}
