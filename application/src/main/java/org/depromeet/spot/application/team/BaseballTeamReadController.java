package org.depromeet.spot.application.team;

import java.util.List;

import org.depromeet.spot.application.team.dto.response.BaseballTeamLogoResponse;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.team.BaseballTeamReadUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
public class BaseballTeamReadController {

    private final BaseballTeamReadUsecase baseballTeamReadUsecase;

    @GetMapping("/baseball-teams")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "SPOT에서 관리하는 모든 야구 팀 정보를 조회한다.")
    public List<BaseballTeamLogoResponse> findAll() {
        List<BaseballTeam> infos = baseballTeamReadUsecase.findAll();
        return infos.stream().map(BaseballTeamLogoResponse::from).toList();
    }
}
