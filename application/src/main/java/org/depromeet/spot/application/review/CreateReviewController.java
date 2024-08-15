package org.depromeet.spot.application.review;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.review.dto.request.CreateAdminReviewRequest;
import org.depromeet.spot.application.review.dto.request.CreateReviewRequest;
import org.depromeet.spot.application.review.dto.response.BaseReviewResponse;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateReviewResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "리뷰")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CreateReviewController {

    private final CreateReviewUsecase createReviewUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "특정 좌석에 신규 리뷰를 추가한다.")
    @PostMapping("/blocks/{blockId}/seats/{seatNumber}/reviews")
    public BaseReviewResponse create(
            @PathVariable @Positive @NotNull final Long blockId,
            @PathVariable @Positive @NotNull final Integer seatNumber,
            @Parameter(hidden = true) Long memberId,
            @RequestBody @Valid CreateReviewRequest request) {
        CreateReviewResult result =
                createReviewUsecase.create(blockId, seatNumber, memberId, request.toCommand());
        return BaseReviewResponse.from(result);
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "[어드민] 특정 열에 신규 리뷰를 추가한다.")
    @PostMapping(value = "/stadiums/{stadiumId}/blocks/{blockCode}/rows/{rowNumber}/reviews")
    public void createAll(
            @PathVariable @Positive @NotNull final long stadiumId,
            @PathVariable @Positive @NotNull final String blockCode,
            @PathVariable @Positive @NotNull final int rowNumber,
            @Parameter(hidden = true) Long memberId,
            @RequestPart @Valid CreateAdminReviewRequest data,
            @RequestPart @Size(min = 1, max = 3) List<MultipartFile> images) {
        createReviewUsecase.createAdmin(
                stadiumId, blockCode, rowNumber, memberId, data.toCommand(images));
    }
}
