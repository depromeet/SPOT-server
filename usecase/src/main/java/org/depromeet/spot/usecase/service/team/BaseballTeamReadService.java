package org.depromeet.spot.usecase.service.team;

import java.util.List;

import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.BaseballTeamReadUsecase;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class BaseballTeamReadService implements BaseballTeamReadUsecase {

    private final BaseballTeamRepository baseballTeamRepository;

    @Override
    public List<BaseballTeam> findAll() {
        return baseballTeamRepository.findAll();
    }
}
