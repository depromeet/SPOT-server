package org.depromeet.spot.application.member.service;

import org.depromeet.spot.application.member.service.port.MemberService;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.jpa.member.repository.port.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member create(final String name) {
        val member = new Member(null, name);
        return memberRepository.save(member);
    }
}
