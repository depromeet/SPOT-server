package org.depromeet.spot.usecase.service.member;

import java.time.LocalDateTime;

import org.depromeet.spot.common.exception.member.MemberException.MemberNicknameConflictException;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.member.MemberUsecase;
import org.depromeet.spot.usecase.port.in.member.ReadMemberUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.oauth.OauthRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements MemberUsecase {

    private final OauthRepository oauthRepository;

    private final MemberRepository memberRepository;

    private final ReadMemberUsecase readMemberUsecase;

    private final ReadBaseballTeamUsecase readBaseballTeamUsecase;

    @Override
    public Member create(String accessToken, Member member) {
        if (memberRepository.existsByNickname(member.getNickname())) {
            throw new MemberNicknameConflictException();
        }
        Member memberResult = oauthRepository.getRegisterUserInfo(accessToken, member);
        Member existedMember = memberRepository.findByIdToken(memberResult.getIdToken());
        if (existedMember != null) {
            return existedMember;
        }

        return memberRepository.save(memberResult);
    }

    @Override
    public Member login(String accessToken) {
        Member memberResult = oauthRepository.getLoginUserInfo(accessToken);
        Member existedMember = memberRepository.findByIdToken(memberResult.getIdToken());
        return existedMember;
    }

    @Override
    public Boolean duplicatedNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname))
            throw new MemberNicknameConflictException();
        return Boolean.FALSE;
    }

    @Override
    public String getAccessToken(String idCode) {
        return oauthRepository.getKakaoAccessToken(idCode);
    }

    @Transactional
    @Override
    public Boolean deleteMember(String accessToken) {
        Member memberResult = oauthRepository.getLoginUserInfo(accessToken);
        memberRepository.findByIdToken(memberResult.getIdToken());

        memberRepository.deleteByIdToken(memberResult.getIdToken());
        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfo findMemberInfo(Long memberId) {
        Member member = readMemberUsecase.findById(memberId);
        BaseballTeam baseballTeam = readBaseballTeamUsecase.findById(member.getTeamId());

        return MemberInfo.of(member, baseballTeam);
    }

    @Override
    public void softDelete(Long memberId) {
        memberRepository.updateDeletedAt(memberId, LocalDateTime.now());
    }
}
