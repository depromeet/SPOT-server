package org.depromeet.spot.application.review;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.review.dto.request.BlockReviewRequest;
import org.depromeet.spot.application.review.dto.request.MyReviewRequest;
import org.depromeet.spot.application.review.dto.response.ReviewListResponse;
import org.depromeet.spot.domain.review.ReviewListResult;
import org.depromeet.spot.usecase.port.in.review.ReviewReadUsecase;
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
public class ReviewReadController {

    private final ReviewReadUsecase reviewReadUsecase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stadiums/{stadiumId}/blocks/{blockId}/reviews")
    @Operation(summary = "특정 야구장의 특정 블록에 대한 리뷰 목록을 조회한다.")
    public ReviewListResponse findReviewsByBlockId(
            @PathVariable("stadiumId")
                    @NotNull
                    @Positive
                    @Parameter(description = "야구장 PK", required = true)
                    Long stadiumId,
            @PathVariable("blockId")
                    @NotNull
                    @Positive
                    @Parameter(description = "블록 PK", required = true)
                    Long blockId,
            @ModelAttribute BlockReviewRequest request) {
        ReviewListResult result =
                reviewReadUsecase.findReviewsByBlockId(
                        stadiumId,
                        blockId,
                        request.rowId(),
                        request.seatNumber(),
                        request.offset(),
                        request.limit());
        return ReviewListResponse.from(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews")
    @Operation(
            summary = " 자신이 작성한 리뷰 목록을 조회한다.",
            description = "연도와 월로 필터링할 수 있다. 필터링 없이 전체를 조회하려면 year와 month를 null로 입력한다.")
    public ReviewListResponse findMyReviews(@ModelAttribute MyReviewRequest request) {
        ReviewListResult result =
                reviewReadUsecase.findMyReviews(
                        request.userId(),
                        request.offset(),
                        request.limit(),
                        request.year(),
                        request.month());
        return ReviewListResponse.from(result);
    }
}
