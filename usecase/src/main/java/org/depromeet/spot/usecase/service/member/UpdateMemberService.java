package org.depromeet.spot.usecase.service.member;

import java.util.Set;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.member.ReadLevelUsecase;
import org.depromeet.spot.usecase.port.in.member.ReadMemberUsecase;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateMemberService implements UpdateMemberUsecase {

    private final MemberRepository memberRepository;
    private final ReadLevelUsecase readLevelUsecase;
    private final ReadMemberUsecase readMemberUsecase;
    private final ReadBaseballTeamUsecase readBaseballTeamUsecase;

    @Override
    public Member updateProfile(final Long memberId, UpdateProfileCommand command) {
        Member member = readMemberUsecase.findById(memberId);
        readBaseballTeamUsecase.areAllTeamIdsExist(Set.of(command.teamId()));
        Member updateMember =
                member.updateProfile(command.profileImage(), command.nickname(), command.teamId());
        return memberRepository.updateProfile(updateMember);
    }

    @Override
    public Member updateLevel(Member member, long reviewCnt) {
        final int newLevelValue = Level.calculateLevel(reviewCnt);
        Level newLevel = readLevelUsecase.findByValue(newLevelValue);
        Member updateMember = member.updateLevel(newLevel);
        return memberRepository.updateLevel(updateMember);
    }
}
