package org.depromeet.spot.application.review.scrap;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase;
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
public class ReviewScrapController {
    private final ReviewScrapUsecase reviewScrapUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 리뷰를 스크랩한다. 만약 이전에 스크랩했던 리뷰라면, 스크랩을 취소한다.")
    @PostMapping("/{reviewId}/scrap")
    public boolean toggleScrap(
            @PathVariable @Positive @NotNull final Long reviewId,
            @Parameter(hidden = true) Long memberId) {
        return reviewScrapUsecase.toggleScrap(memberId, reviewId);
    }
}
