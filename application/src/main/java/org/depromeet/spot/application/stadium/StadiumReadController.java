package org.depromeet.spot.application.stadium;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.stadium.dto.response.StadiumHomeTeamInfoResponse;
import org.depromeet.spot.application.stadium.dto.response.StadiumInfoWithSeatChartResponse;
import org.depromeet.spot.application.stadium.dto.response.StadiumNameInfoResponse;
import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumHomeTeamInfo;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumInfoWithSeatChart;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase.StadiumNameInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "경기장")
@RequiredArgsConstructor
@RequestMapping("/api/v1/stadiums")
public class StadiumReadController {

    private final StadiumReadUsecase stadiumReadUsecase;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "SPOT에서 관리하는 모든 야구 경기장 정보를 조회한다.")
    public List<StadiumHomeTeamInfoResponse> findAllStadiums() {
        try {
            throw new StadiumNotFoundException();
        } catch (StadiumNotFoundException e) {
            System.out.println(e.getMessage());
            Sentry.captureException(e);
        }

        List<StadiumHomeTeamInfo> infos = stadiumReadUsecase.findAllStadiums();
        return infos.stream().map(StadiumHomeTeamInfoResponse::from).toList();
    }

    @GetMapping("/names")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "SPOT에서 관리하는 모든 야구 경기장의 이름들을 조회한다.")
    public List<StadiumNameInfoResponse> findAllNames() {
        List<StadiumNameInfo> infos = stadiumReadUsecase.findAllNames();
        return infos.stream()
                .map(t -> new StadiumNameInfoResponse(t.getId(), t.getName(), t.isActive()))
                .toList();
    }

    @GetMapping("/{stadiumId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 야구 경기장의 상세 정보를 좌석 배치도와 함께 조회한다.")
    public StadiumInfoWithSeatChartResponse findWithSeatChartById(
            @PathVariable("stadiumId")
                    @NotNull
                    @Positive
                    @Parameter(name = "stadiumId", description = "야구 경기장 PK", required = true)
                    final Long stadiumId) {
        StadiumInfoWithSeatChart info = stadiumReadUsecase.findWithSeatChartById(stadiumId);
        return StadiumInfoWithSeatChartResponse.from(info);
    }
}
