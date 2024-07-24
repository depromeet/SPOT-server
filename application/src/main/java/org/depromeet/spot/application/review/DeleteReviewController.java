package org.depromeet.spot.application.review;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.review.dto.response.DeleteResponse;
import org.depromeet.spot.usecase.port.in.review.DeleteReviewUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class DeleteReviewController {

    private final DeleteReviewUsecase deleteReviewUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "리뷰를 삭제한다. (soft delete)")
    @DeleteMapping("/reviews/{reviewId}")
    public DeleteResponse deleteReview(
            @PathVariable Long reviewId, @Parameter(hidden = true) Long memberId) {
        Long deletedReviewId = deleteReviewUsecase.deleteReview(reviewId, memberId);

        return DeleteResponse.from(deletedReviewId);
    }
}
