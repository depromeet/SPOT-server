package org.depromeet.spot.application.review;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.review.dto.request.BlockReviewRequest;
import org.depromeet.spot.application.review.dto.request.MyReviewRequest;
import org.depromeet.spot.application.review.dto.response.BaseReviewResponse;
import org.depromeet.spot.application.review.dto.response.BlockReviewListResponse;
import org.depromeet.spot.application.review.dto.response.MyRecentReviewResponse;
import org.depromeet.spot.application.review.dto.response.MyReviewListResponse;
import org.depromeet.spot.application.review.dto.response.ReviewMonthsResponse;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class ReadReviewController {

    private final ReadReviewUsecase readReviewUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stadiums/{stadiumId}/blocks/{blockCode}/reviews")
    @Operation(summary = "특정 야구장의 특정 블록에 대한 리뷰 목록을 조회한다.")
    public BlockReviewListResponse findReviewsByBlockId(
            @PathVariable("stadiumId")
                    @NotNull
                    @Positive
                    @Parameter(description = "야구장 PK", required = true)
                    Long stadiumId,
            @PathVariable("blockCode") @NotNull @Parameter(description = "블록 코드", required = true)
                    String blockCode,
            @ModelAttribute @Valid BlockReviewRequest request,
            @ParameterObject
                    @PageableDefault(
                            size = 20,
                            page = 0,
                            sort = "dateTime",
                            direction = Sort.Direction.DESC)
                    Pageable pageable) {

        ReadReviewUsecase.BlockReviewListResult result =
                readReviewUsecase.findReviewsByStadiumIdAndBlockCode(
                        stadiumId,
                        blockCode,
                        request.rowNumber(),
                        request.seatNumber(),
                        request.year(),
                        request.month(),
                        pageable);
        return BlockReviewListResponse.from(
                result, request.rowNumber(), request.seatNumber(), request.year(), request.month());
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/months")
    @Operation(summary = "리뷰가 작성된 년도와 월 정보를 조회한다.")
    public ReviewMonthsResponse findReviewMonths(@Parameter(hidden = true) Long memberId) {

        List<ReviewYearMonth> yearMonths = readReviewUsecase.findReviewMonths(memberId);
        return ReviewMonthsResponse.from(yearMonths);
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews")
    @Operation(
            summary = "자신이 작성한 리뷰 목록을 조회한다.",
            description = "연도와 월로 필터링할 수 있다. 필터링 없이 전체를 조회하려면 year와 month를 null로 입력한다.")
    public MyReviewListResponse findMyReviews(
            @Parameter(hidden = true) Long memberId,
            @ModelAttribute @Valid MyReviewRequest request,
            @ParameterObject
                    @PageableDefault(
                            size = 20,
                            page = 0,
                            sort = "dateTime",
                            direction = Sort.Direction.DESC)
                    Pageable pageable) {

        ReadReviewUsecase.MyReviewListResult result =
                readReviewUsecase.findMyReviewsByUserId(
                        memberId, request.year(), request.month(), pageable);
        return MyReviewListResponse.from(result, request.year(), request.month());
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/recentReview")
    @Operation(summary = "자신이 작성한 최근 리뷰를 조회한다.")
    public MyRecentReviewResponse findMyRecentReview(@Parameter(hidden = true) Long memberId) {

        ReadReviewUsecase.MyRecentReviewResult result =
                readReviewUsecase.findLastReviewByMemberId(memberId);
        return MyRecentReviewResponse.from(result);
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 id(pk)로 특정 리뷰를 조회한다.")
    public BaseReviewResponse findReviewByReviewId(
            @Parameter(hidden = true) Long memberId,
            @PathVariable("reviewId") @NotBlank @Parameter(description = "리뷰 PK", required = true)
                    Long reviewId) {
        ReadReviewUsecase.ReviewResult reviewResult = readReviewUsecase.findReviewById(reviewId);
        return BaseReviewResponse.from(reviewResult.review());
    }
}
