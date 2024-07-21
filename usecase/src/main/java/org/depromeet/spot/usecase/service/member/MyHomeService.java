package org.depromeet.spot.usecase.service.member;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase;
import org.depromeet.spot.usecase.port.in.member.ReadMemberUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadBaseballTeamUsecase;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyHomeService implements MyHomeUsecase {

    private final ReadBaseballTeamUsecase readBaseballTeamUsecase;

    private final ReadMemberUsecase readMemberUsecase;

    @Override
    public MyHome findMyHomeInfo(Long memberId) {
        BaseballTeam baseballTeam = readBaseballTeamUsecase.findById(memberId);

        Member member = readMemberUsecase.findById(memberId);

        // TODO : 최근 리뷰 불러와서 데이터 함께 넣어주기.

        return MyHome.builder().baseballTeam(baseballTeam).member(member).build();
    }
}
