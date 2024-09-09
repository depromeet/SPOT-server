package org.depromeet.spot.application.review.scrap;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.review.dto.request.PageRequest;
import org.depromeet.spot.application.review.dto.request.scrap.MyScrapRequest;
import org.depromeet.spot.application.review.dto.response.scrap.MyScrapListResponse;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase.MyScrapListResult;
import org.depromeet.spot.usecase.service.event.MixpanelEvent;
import org.depromeet.spot.usecase.service.event.MixpanelEvent.MixpanelEventName;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ReviewScrapUsecase reviewScrapUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "특정 리뷰를 스크랩한다. 만약 이전에 스크랩했던 리뷰라면, 스크랩을 취소한다.")
    @PostMapping("/{reviewId}/scrap")
    public boolean toggleScrap(
            @PathVariable @Positive @NotNull final Long reviewId,
            @Parameter(hidden = true) Long memberId) {
        boolean result = reviewScrapUsecase.toggleScrap(memberId, reviewId);

        // 믹스패널 이벤트(스크랩 수) 발생
        applicationEventPublisher.publishEvent(
                new MixpanelEvent(MixpanelEventName.REVIEW_SCRAP_COUNT, String.valueOf(memberId)));

        return result;
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/scraps")
    @Operation(
            summary = "자신이 스크랩한 리뷰 목록을 조회한다.",
            description = "stadiumId,  months, good, bad로 필터링 가능하다.")
    public MyScrapListResponse findMyReviews(
            @Parameter(hidden = true) Long memberId,
            @Valid MyScrapRequest request,
            @Valid PageRequest pageRequest) {

        MyScrapListResult result =
                reviewScrapUsecase.findMyScrappedReviews(
                        memberId, request.toCommand(), pageRequest.toCommand());
        return MyScrapListResponse.from(result, request.toCommand());
    }
}
