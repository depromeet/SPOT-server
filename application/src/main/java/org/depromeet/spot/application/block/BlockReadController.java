package org.depromeet.spot.application.block;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.block.dto.response.BlockCodeInfoResponse;
import org.depromeet.spot.usecase.port.in.block.BlockReadUsecase;
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
@Tag(name = "블록")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BlockReadController {

    private final BlockReadUsecase blockReadUsecase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stadiums/{stadiumId}/sections/{sectionId}/blocks")
    @Operation(summary = "특정 야구 경기장 특정 구역 내의 블록 리스트를 조회한다.")
    public List<BlockCodeInfoResponse> findCodeInfosByStadium(
            @PathVariable("stadiumId")
                    @NotNull
                    @Positive
                    @Parameter(name = "stadiumId", description = "야구 경기장 PK", required = true)
                    final Long stadiumId,
            @PathVariable("sectionId")
                    @NotNull
                    @Positive
                    @Parameter(name = "sectionId", description = "구역 PK", required = true)
                    final Long sectionId) {}
}
