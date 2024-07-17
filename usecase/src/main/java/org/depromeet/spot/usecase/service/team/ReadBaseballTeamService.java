package org.depromeet.spot.usecase.service.team;

import java.util.List;
import java.util.Set;

import org.depromeet.spot.common.exception.team.TeamException.BaseballTeamNotFoundException;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.ReadBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class ReadBaseballTeamService implements ReadBaseballTeamUsecase {

    private final BaseballTeamRepository baseballTeamRepository;

    @Override
    public BaseballTeam findById(final Long id) {
        return baseballTeamRepository.findById(id);
    }

    @Override
    public List<BaseballTeam> findAll() {
        return baseballTeamRepository.findAll();
    }

    @Override
    public void areAllTeamIdsExist(Set<Long> teamIds) {
        for (Long teamId : teamIds) {
            if (!baseballTeamRepository.existsById(teamId)) {
                throw new BaseballTeamNotFoundException(teamId);
            }
        }
    }
}
