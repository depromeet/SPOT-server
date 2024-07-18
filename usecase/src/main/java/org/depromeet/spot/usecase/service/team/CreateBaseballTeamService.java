package org.depromeet.spot.usecase.service.team;

import java.util.List;

import org.depromeet.spot.common.exception.team.TeamException.DuplicateTeamNameException;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.CreateBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@Transactional
@RequiredArgsConstructor
public class CreateBaseballTeamService implements CreateBaseballTeamUsecase {

    private final BaseballTeamRepository baseballTeamRepository;

    @Override
    public void saveAll(List<BaseballTeam> teams) {
        List<String> names = teams.stream().map(BaseballTeam::getName).toList();
        if (baseballTeamRepository.existsByNameIn(names)) {
            throw new DuplicateTeamNameException();
        }
        baseballTeamRepository.saveAll(teams);
    }
}
