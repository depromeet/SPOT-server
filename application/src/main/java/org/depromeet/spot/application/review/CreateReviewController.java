package org.depromeet.spot.application.review;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.review.dto.request.CreateReviewRequest;
import org.depromeet.spot.application.review.dto.response.BaseReviewResponse;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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
        CreateReviewUsecase.CreateReviewResult result =
                createReviewUsecase.create(blockId, seatNumber, memberId, request.toCommand());
        return BaseReviewResponse.from(result);
    }
}
