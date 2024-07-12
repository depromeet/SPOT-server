package org.depromeet.spot.usecase.service.stadium;

import java.util.List;

import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StadiumReadService implements StadiumReadUsecase {

    @Override
    public List<StadiumHomeTeamInfo> findAllStadiums() {
        return null;
    }

    @Override
    public List<StadiumNameInfo> findAllNames() {
        return null;
    }

    @Override
    public StadiumInfoWithSeatChart findWithSeatChartById(Long id) {
        return null;
    }
}
