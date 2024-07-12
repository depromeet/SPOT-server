package org.depromeet.spot.usecase.service.team;

import java.util.List;

import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.StadiumHomeTeamReadUsecase;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StadiumHomeTeamReadService implements StadiumHomeTeamReadUsecase {

    private final BaseballTeamRepository baseballTeamRepository;

    @Override
    public List<HomeTeamInfo> findByStadium(final Long stadiumId) {
        List<BaseballTeam> teams = baseballTeamRepository.findAllHomeTeamByStadium(stadiumId);
        return teams.stream()
                .map(t -> new HomeTeamInfo(t.getId(), t.getAlias(), t.getLabelRgbCode()))
                .toList();
    }
}
