package org.depromeet.spot.application.review.like;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.usecase.port.in.review.like.ReviewLikeUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "리뷰 공감")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewLikeController {

    //    private final ApplicationEventPublisher applicationEventPublisher;

    private final ReviewLikeUsecase reviewLikeUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 리뷰에 공감한다. 만약 이전에 공감했던 리뷰라면, 공감을 취소한다.")
    @PostMapping("/{reviewId}/like")
    public void toggleLike(
            @PathVariable @Positive @NotNull final Long reviewId,
            @Parameter(hidden = true) Long memberId) {
        boolean result = reviewLikeUsecase.toggleLike(memberId, reviewId);
        //        if (result) {
        //            // 리뷰 공감 추이 이벤트 발생
        //            applicationEventPublisher.publishEvent(
        //                    new MixpanelEvent(
        //                            MixpanelEventName.REVIEW_LIKE_COUNT,
        // String.valueOf(memberId)));
        //        }
    }
}
