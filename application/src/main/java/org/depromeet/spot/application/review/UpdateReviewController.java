package org.depromeet.spot.application.review;

import jakarta.validation.Valid;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.review.dto.request.UpdateReviewRequest;
import org.depromeet.spot.application.review.dto.response.BaseReviewResponse;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "리뷰")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UpdateReviewController {

    private final UpdateReviewUsecase updateReviewUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/reviews/{reviewId}")
    @Operation(summary = "특정 리뷰를 수정한다.")
    public BaseReviewResponse updateReview(
            @Parameter(hidden = true) Long memberId,
            @PathVariable("reviewId") @Parameter(description = "리뷰 PK", required = true)
                    Long reviewId,
            @RequestBody @Valid UpdateReviewRequest request) {

        CreateReviewUsecase.ReviewResult result =
                updateReviewUsecase.updateReview(memberId, reviewId, request.toCommand());

        return BaseReviewResponse.from(result);
    }
}
