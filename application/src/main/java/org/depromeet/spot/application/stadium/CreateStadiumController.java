package org.depromeet.spot.application.stadium;

import org.depromeet.spot.application.stadium.dto.response.StadiumResponse;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.stadium.CreateStadiumUsecase;
import org.depromeet.spot.usecase.port.in.stadium.CreateStadiumUsecase.CreateStadiumReq;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "경기장")
@RequiredArgsConstructor
@RequestMapping("/api/v1/stadiums")
public class CreateStadiumController {

    private final CreateStadiumUsecase createStadiumUsecase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "신규 야구 경기장을 등록한다.")
    public StadiumResponse create(
            @RequestParam("name") String name,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam("seatingChartImage") MultipartFile seatingChartImage,
            @RequestParam("labeledSeatingChartImage") MultipartFile labeledSeatingChartImage,
            @RequestParam("isActive") boolean isActive) {
        CreateStadiumReq req =
                CreateStadiumReq.builder()
                        .name(name)
                        .mainImage(mainImage)
                        .seatingChartImage(seatingChartImage)
                        .labeledSeatingChartImage(labeledSeatingChartImage)
                        .isActive(isActive)
                        .build();
        Stadium stadium = createStadiumUsecase.create(req);
        return StadiumResponse.from(stadium);
    }
}
