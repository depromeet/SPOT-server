package org.depromeet.spot.usecase.service.stadium;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.stadium.CreateStadiumUsecase;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateStadiumService implements CreateStadiumUsecase {

    private final StadiumRepository stadiumRepository;

    @Override
    public Stadium create(CreateStadiumReq request) {

        return null;
    }
}
