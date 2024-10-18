package org.depromeet.spot.usecase.service.member;

import java.time.LocalDateTime;
import java.util.Optional;

import org.depromeet.spot.common.exception.member.MemberException.InactiveMemberException;
import org.depromeet.spot.common.exception.member.MemberException.MemberConflictException;
import org.depromeet.spot.common.exception.member.MemberException.MemberNicknameConflictException;
import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.member.MemberUsecase;
import org.depromeet.spot.usecase.port.in.member.ReadMemberUsecase;
import org.depromeet.spot.usecase.port.in.member.level.ReadLevelUsecase;
import org.depromeet.spot.usecase.port.in.review.DeleteReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
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
    private final ReadLevelUsecase readLevelUsecase;
    private final ReadBaseballTeamUsecase readBaseballTeamUsecase;
    private final DeleteReviewUsecase deleteReviewUsecase;

    private final ReadReviewUsecase readReviewUsecase;

    @Override
    public Member create(String accessToken, Member member) {
        if (memberRepository.existsByNickname(member.getNickname())) {
            throw new MemberNicknameConflictException();
        }
        Member memberResult = oauthRepository.getKakaoRegisterUserInfo(accessToken, member);
        Level initialLevel = readLevelUsecase.findInitialLevel();

        // 이미 가입된 유저 Exception
        Optional<Member> existedMember = memberRepository.findByIdToken(memberResult.getIdToken());
        if (existedMember.isPresent()) {
            throw new MemberConflictException();
        }

        return memberRepository.save(memberResult, initialLevel);
    }

    @Override
    public Member login(String accessToken) {
        Member memberResult = oauthRepository.getLoginUserInfo(accessToken);
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
        memberRepository
                .findByIdToken(memberResult.getIdToken())
                .orElseThrow(MemberNotFoundException::new);

        memberRepository.deleteByIdToken(memberResult.getIdToken());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfo findMemberInfo(Long memberId) {
        Member member = readMemberUsecase.findById(memberId);
        long reviewCount = readReviewUsecase.countByIdByMemberId(memberId);
        long reviewCntToLevelUp = Level.calculateReviewCntToLevelUp(reviewCount);

        if (member.getTeamId() == null) {
            return MemberInfo.of(member, reviewCntToLevelUp);
        }
        BaseballTeam baseballTeam = readBaseballTeamUsecase.findById(member.getTeamId());

        return MemberInfo.of(member, baseballTeam, reviewCntToLevelUp);
    }

    @Transactional
    @Override
    public void softDelete(Long memberId) {

        //        멤버 삭제 전 리뷰 삭제가 우선이 되어야함!
        deleteReviewUsecase.deleteAllReviewOwnedByMemberId(memberId);

        memberRepository.updateDeletedAt(memberId, LocalDateTime.now());
    }

    @Override
    public int membersCount() {
        return memberRepository.membersCount();
    }
}
