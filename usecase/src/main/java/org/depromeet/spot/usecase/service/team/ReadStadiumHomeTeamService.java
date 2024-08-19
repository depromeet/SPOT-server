package org.depromeet.spot.usecase.service.team;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase;
import org.depromeet.spot.usecase.port.out.team.HomeTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadStadiumHomeTeamService implements ReadStadiumHomeTeamUsecase {

    private final HomeTeamRepository homeTeamRepository;

    @Override
    public List<HomeTeamInfo> findByStadium(final Long stadiumId) {
        List<BaseballTeam> teams = homeTeamRepository.findAllHomeTeamByStadium(stadiumId);
        return teams.stream()
                .map(
                        t ->
                                new HomeTeamInfo(
                                        t.getId(), t.getAlias(), t.getLabelFontColor().getValue()))
                .toList();
    }

    @Override
    public Map<Stadium, List<BaseballTeam>> findAllStadiumHomeTeam() {
        return homeTeamRepository.findAllStadiumHomeTeam();
    }
}
