package org.depromeet.spot.usecase.service.stadium;

import org.depromeet.spot.domain.media.MediaProperty;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.stadium.CreateStadiumUsecase;
import org.depromeet.spot.usecase.port.out.media.ImageUploadPort;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateStadiumService implements CreateStadiumUsecase {

    private final StadiumRepository stadiumRepository;
    private final ImageUploadPort imageUploadPort;

    @Override
    public Stadium create(CreateStadiumReq request) {
        final String name = request.name();
        final String mainImageUrl =
                imageUploadPort.upload(name, request.mainImage(), MediaProperty.STADIUM);
        final String seatChartUrl =
                imageUploadPort.upload(
                        name, request.seatingChartImage(), MediaProperty.STADIUM_SEAT);
        final String labelSeatChartUrl =
                imageUploadPort.upload(
                        name, request.labeledSeatingChartImage(), MediaProperty.STADIUM_SEAT_LABEL);
        Stadium stadium =
                Stadium.builder()
                        .name(name)
                        .mainImage(mainImageUrl)
                        .seatingChartImage(seatChartUrl)
                        .labeledSeatingChartImage(labelSeatChartUrl)
                        .isActive(request.isActive())
                        .build();
        return stadiumRepository.save(stadium);
    }
}
