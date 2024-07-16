package org.depromeet.spot.application.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

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
            @RequestParam(required = false) @Parameter(description = "열 ID (필터링)") Long rowId,
            @RequestParam(required = false) @Parameter(description = "좌석 번호 (필터링)") Long seatNumber,
            @RequestParam(defaultValue = "0")
                    @PositiveOrZero
                    @Parameter(description = "시작 위치 (기본값: 0)")
                    int offset,
            @RequestParam(defaultValue = "10")
                    @Positive
                    @Max(50)
                    @Parameter(description = "조회할 리뷰 수 (기본값: 10, 최대: 50)")
                    int limit) {

        ReviewListResult result =
                reviewReadUsecase.findReviewsByBlockId(
                        stadiumId, blockId, rowId, seatNumber, offset, limit);
        return ReviewListResponse.from(result);
    }
}
