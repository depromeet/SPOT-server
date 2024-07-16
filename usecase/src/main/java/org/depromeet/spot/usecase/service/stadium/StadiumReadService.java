package org.depromeet.spot.usecase.service.stadium;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase.HomeTeamInfo;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class StadiumReadService implements StadiumReadUsecase {

    private final ReadStadiumHomeTeamUsecase readStadiumHomeTeamUsecase;
    private final StadiumRepository stadiumRepository;

    @Override
    public List<StadiumHomeTeamInfo> findAllStadiums() {
        Map<Stadium, List<BaseballTeam>> stadiumHomeTeams =
                readStadiumHomeTeamUsecase.findAllStadiumHomeTeam();
        return stadiumHomeTeams.entrySet().stream()
                .map(
                        entry -> {
                            Stadium stadium = entry.getKey();
                            List<BaseballTeam> teams = entry.getValue();
                            List<HomeTeamInfo> homeTeamInfos =
                                    teams.stream()
                                            .map(
                                                    t ->
                                                            new HomeTeamInfo(
                                                                    t.getId(),
                                                                    t.getAlias(),
                                                                    t.getLabelRgbCode()))
                                            .collect(Collectors.toList());

                            return new StadiumHomeTeamInfo(
                                    stadium.getId(),
                                    stadium.getName(),
                                    homeTeamInfos,
                                    stadium.getMainImage(),
                                    stadium.isActive());
                        })
                .toList();
    }

    @Override
    public List<StadiumNameInfo> findAllNames() {
        List<Stadium> stadiums = stadiumRepository.findAll();
        return stadiums.stream().map(s -> new StadiumNameInfo(s.getId(), s.getName())).toList();
    }

    @Override
    public StadiumInfoWithSeatChart findWithSeatChartById(final Long id) {
        Stadium stadium = stadiumRepository.findById(id);
        List<HomeTeamInfo> homeTeams = readStadiumHomeTeamUsecase.findByStadium(id);
        return StadiumInfoWithSeatChart.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .homeTeams(homeTeams)
                .seatChartWithLabel(stadium.getLabeledSeatingChartImage())
                .build();
    }

    @Override
    public Stadium findById(final Long id) {
        return stadiumRepository.findById(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return stadiumRepository.existsById(id);
    }
}