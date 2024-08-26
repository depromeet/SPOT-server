package org.depromeet.spot.usecase.service.oauth;

import org.depromeet.spot.common.exception.member.MemberException.InactiveMemberException;
import org.depromeet.spot.common.exception.member.MemberException.MemberNicknameConflictException;
import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.usecase.port.in.member.level.ReadLevelUsecase;
import org.depromeet.spot.usecase.port.in.oauth.OauthUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.oauth.OauthRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService implements OauthUsecase {

    private final OauthRepository oauthRepository;
    private final MemberRepository memberRepository;
    private final ReadLevelUsecase readLevelUsecase;

    @Override
    public Member create(String accessToken, Member member) {
        if (memberRepository.existsByNickname(member.getNickname())) {
            throw new MemberNicknameConflictException();
        }
        Member memberResult = oauthRepository.getOauthRegisterUserInfo(accessToken, member);
        Level initialLevel = readLevelUsecase.findInitialLevel();

        return memberRepository.save(memberResult, initialLevel);
    }

    @Override
    public Member login(SnsProvider snsProvider, String token) {
        String accessToken;
        switch (snsProvider) {
            case KAKAO:
                accessToken = token;
                break;
            default:
                accessToken = oauthRepository.getOauthAccessToken(snsProvider, token);
                break;
        }
        Member memberResult = oauthRepository.getOauthLoginUserInfo(snsProvider, accessToken);
        Member existedMember = memberRepository.findByIdToken(memberResult.getIdToken());

        // 회원 탈퇴 유저일 경우 재가입
        if (existedMember.getDeletedAt() != null) {
            throw new InactiveMemberException();
        }
        return existedMember;
    }

    @Override
    public String getOauthAccessToken(SnsProvider snsProvider, String authorizationCode) {
        return oauthRepository.getOauthAccessToken(snsProvider, authorizationCode);
    }
}
