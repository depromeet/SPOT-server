package org.depromeet.spot.usecase.port.in.stadium;

import org.depromeet.spot.domain.stadium.Stadium;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;

public interface CreateStadiumUsecase {

    Stadium create(CreateStadiumReq request);

    @Builder
    record CreateStadiumReq(
            String name,
            MultipartFile mainImage,
            MultipartFile seatingChartImage,
            MultipartFile labeledSeatingChartImage,
            boolean isActive) {}
}
