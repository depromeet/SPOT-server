package org.depromeet.spot.usecase.service.member;

import java.util.Optional;

import org.depromeet.spot.common.exception.member.MemberException.MemberNicknameConflictException;
import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
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
        Optional<Member> existedMember = memberRepository.findByIdToken(memberResult.getIdToken());
        if (existedMember.isPresent()) {
            return existedMember.get();
        }

        return memberRepository.save(memberResult);
    }

    @Override
    public Member login(String accessToken) {
        Member memberResult = oauthRepository.getLoginUserInfo(accessToken);
        Optional<Member> existedMember = memberRepository.findByIdToken(memberResult.getIdToken());
        if (existedMember.isEmpty()) {
            // TODO : 404 말고 사용되지 않는 Exception 코드가 필요함.
            //            throw new MemberNotFoundException();
            return null;
        }
        return existedMember.get();
    }

    @Override
    public boolean duplicatedNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname))
            throw new MemberNicknameConflictException();
        return false;
    }

    @Override
    public String getAccessToken(String idCode) {
        return oauthRepository.getKakaoAccessToken(idCode);
    }

    @Transactional
    @Override
    public boolean deleteMember(String accessToken) {
        Member memberResult = oauthRepository.getLoginUserInfo(accessToken);
        Optional<Member> existedMember = memberRepository.findByIdToken(memberResult.getIdToken());

        // 멤버 없으면 오류 출력
        if (existedMember.isEmpty()) {
            throw new MemberNotFoundException();
        }
        memberRepository.deleteByIdToken(memberResult.getIdToken());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfo findMemberInfo(Long memberId) {
        Member member = readMemberUsecase.findById(memberId);
        BaseballTeam baseballTeam = readBaseballTeamUsecase.findById(member.getTeamId());

        return MemberInfo.of(member, baseballTeam);
    }
}
