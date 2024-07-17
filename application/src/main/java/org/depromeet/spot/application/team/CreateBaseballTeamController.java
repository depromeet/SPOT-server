package org.depromeet.spot.application.team;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.team.dto.request.CreateBaseballTeamReq;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.CreateBaseballTeamUsecase;
import org.depromeet.spot.usecase.port.in.team.CreateHomeTeamUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "야구 팀")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CreateBaseballTeamController {

    private final CreateBaseballTeamUsecase createBaseballTeamUsecase;
    private final CreateHomeTeamUsecase createHomeTeamUsecase;

    @PostMapping("/baseball-teams")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "신규 야구 팀(구단) 정보를 생성한다.")
    public void create(@RequestBody @Valid @NotEmpty List<CreateBaseballTeamReq> requests) {
        List<BaseballTeam> teams = requests.stream().map(CreateBaseballTeamReq::toDomain).toList();
        createBaseballTeamUsecase.saveAll(teams);
    }

    @PostMapping("/stadiums/{stadiumId}/baseball-teams")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "특정 경기장에 홈 팀을 등록한다.")
    public void createHomeTeam(
            @PathVariable @Positive @NotNull final Long stadiumId,
            @RequestBody @NotEmpty List<@Positive @NotNull Long> teamIds) {
        createHomeTeamUsecase.createHomeTeam(stadiumId, teamIds);
    }
}
