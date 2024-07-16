package org.depromeet.spot.application.section;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.section.dto.response.StadiumSectionsResponse;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase.StadiumSections;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "구역")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SectionReadController {

    private final SectionReadUsecase sectionReadUsecase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stadiums/{stadiumId}/sections")
    @Operation(summary = "특정 야구 경기장 내의 모든 구역 정보를 구장 좌석 배치도와 함께 조회한다.")
    public StadiumSectionsResponse findAllByStadium(
            @PathVariable("stadiumId")
                    @NotNull
                    @Positive
                    @Parameter(name = "stadiumId", description = "야구 경기장 PK", required = true)
                    final Long stadiumId) {
        StadiumSections sectionInfo = sectionReadUsecase.findAllByStadium(stadiumId);
        return StadiumSectionsResponse.from(sectionInfo);
    }
}
