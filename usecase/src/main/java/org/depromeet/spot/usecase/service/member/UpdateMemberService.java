package org.depromeet.spot.usecase.service.member;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.depromeet.spot.usecase.port.out.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateMemberService implements UpdateMemberUsecase {

    private final MemberRepository memberRepository;

    @Override
    public Member updateProfile(final Long memberId, UpdateProfileCommand command) {
        return null;
    }

    // FIXME: member 패키지 동시 작업 중이라, 충돌 방지를 위해 findById를 여기에 생성했어요
    // FIXME: 작업 병합 후 ReadUsecase로 이관 필요
    private Member findById(final Long memberId) {}
}
