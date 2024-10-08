package org.depromeet.spot.usecase.service.oauth;

import java.util.Optional;

import org.depromeet.spot.common.exception.member.MemberException.InactiveMemberException;
import org.depromeet.spot.common.exception.member.MemberException.MemberConflictException;
import org.depromeet.spot.common.exception.member.MemberException.MemberNicknameConflictException;
import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
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

        // 이미 가입된 유저일 경우 Exception
        Optional<Member> existedMember = memberRepository.findByIdToken(memberResult.getIdToken());
        if (existedMember.isPresent()) {
            throw new MemberConflictException();
        }

        return existedMember.orElseGet(() -> memberRepository.save(memberResult, initialLevel));
    }

    @Override
    public Member login(SnsProvider snsProvider, String accessToken) {

        Member memberResult = oauthRepository.getOauthLoginUserInfo(snsProvider, accessToken);
        Member existedMember =
                memberRepository
                        .findByIdToken(memberResult.getIdToken())
                        .orElseThrow(MemberNotFoundException::new);

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
