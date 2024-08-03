package org.depromeet.spot.application.member.controller;

import java.util.List;

import org.depromeet.spot.application.member.dto.response.LevelUpTableResponse;
import org.depromeet.spot.usecase.port.in.member.LevelUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "레벨")
@RequestMapping("/api/v1/levelUpConditions")
public class LevelUpTableController {

    private final LevelUsecase levelUsecase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "레벨업 조건 테이블 조회 API")
    public List<LevelUpTableResponse> getLevelUpTable() {
        return levelUsecase.findAllLevels().stream().map(LevelUpTableResponse::from).toList();
    }
}
