package org.depromeet.spot.jpa.team.repository;

import java.util.List;

import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.jpa.team.entity.BaseballTeamEntity;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BaseballTeamRepositoryImpl implements BaseballTeamRepository {

    private final StadiumHomeTeamCustomRepository stadiumHomeTeamCustomRepository;

    @Override
    public List<BaseballTeam> findAllHomeTeamByStadium(final Long stadiumId) {
        List<BaseballTeamEntity> homeTeamEntities =
                stadiumHomeTeamCustomRepository.findAllHomeTeamByStadium(stadiumId);
        return homeTeamEntities.stream().map(BaseballTeamEntity::toDomain).toList();
    }
}
