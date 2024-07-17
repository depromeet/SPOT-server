package org.depromeet.spot.application.section;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.section.dto.request.CreateSectionRequest;
import org.depromeet.spot.usecase.port.in.section.CreateSectionUsecase;
import org.depromeet.spot.usecase.port.in.section.CreateSectionUsecase.CreateSectionCommand;
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
@Tag(name = "구역")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CreateSectionController {

    private final CreateSectionUsecase createSectionUsecase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/stadiums/{stadiumId}/sections")
    @Operation(summary = "특정 경기장의 구역 정보들을 생성한다.")
    public void createAll(
            @PathVariable @Positive @NotNull final Long stadiumId,
            @RequestBody @Valid @NotEmpty List<CreateSectionRequest> requests) {
        List<CreateSectionCommand> commands =
                requests.stream().map(CreateSectionRequest::toCommand).toList();
        createSectionUsecase.createAll(stadiumId, commands);
    }
}
