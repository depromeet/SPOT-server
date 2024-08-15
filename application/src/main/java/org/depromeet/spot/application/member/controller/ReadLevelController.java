package org.depromeet.spot.application.member.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.member.dto.response.LevelUpDialogInfo;
import org.depromeet.spot.application.member.dto.response.LevelUpTableResponse;
import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.usecase.port.in.member.level.ReadLevelUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "레벨")
@RequestMapping("/api/v1/levels")
public class ReadLevelController {

    private final ReadLevelUsecase readLevelUsecase;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "레벨업 조건 테이블 조회 API")
    public List<LevelUpTableResponse> getLevelUpTable() {
        return readLevelUsecase.findAllLevels().stream().map(LevelUpTableResponse::from).toList();
    }

    @GetMapping("/up/info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "레벨업 다이얼로그 조회 API")
    public LevelUpDialogInfo getLevelUpDialogInfo(@RequestParam @NotNull @Positive int nextLevel) {
        Level level = readLevelUsecase.findLevelUpDialogInfo(nextLevel);
        return LevelUpDialogInfo.from(level);
    }
}
