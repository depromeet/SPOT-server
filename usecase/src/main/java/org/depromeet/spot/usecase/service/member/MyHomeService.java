package org.depromeet.spot.usecase.service.member;

import org.depromeet.spot.domain.member.MyHome;
import org.depromeet.spot.usecase.port.in.member.MemberUsecase;
import org.depromeet.spot.usecase.port.in.member.MyHomeUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadBaseballTeamUsecase;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyHomeService implements MyHomeUsecase {

    private final ReadBaseballTeamUsecase readBaseballTeamUsecase;

    private final MemberUsecase memberUsecase;

    @Override
    public MyHome findMyHomeInfo() {

        return null;
    }
}
