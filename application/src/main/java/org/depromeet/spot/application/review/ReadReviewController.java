package org.depromeet.spot.application.review;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.common.annotation.CurrentMember;
import org.depromeet.spot.application.review.dto.request.BlockReviewRequest;
import org.depromeet.spot.application.review.dto.request.MyReviewRequest;
import org.depromeet.spot.application.review.dto.response.BaseReviewResponse;
import org.depromeet.spot.application.review.dto.response.BlockReviewListResponse;
import org.depromeet.spot.application.review.dto.response.MemberInfoOnMyReviewResponse;
import org.depromeet.spot.application.review.dto.response.MyRecentReviewResponse;
import org.depromeet.spot.application.review.dto.response.MyReviewListResponse;
import org.depromeet.spot.application.review.dto.response.ReviewMonthsResponse;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockReviewListResult;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.MyRecentReviewResult;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.MyReviewListResult;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.ReadReviewResult;
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
public class ReadReviewController {

    //    private final ApplicationEventPublisher applicationEventPublisher;

    private final ReadReviewUsecase readReviewUsecase;

    private final UpdateReviewUsecase updateReviewUsecase;

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stadiums/{stadiumId}/blocks/{blockCode}/reviews")
    @Operation(summary = "특정 야구장의 특정 블록에 대한 리뷰 목록을 조회한다.")
    public BlockReviewListResponse findReviewsByBlockId(
            @Parameter(hidden = true) Long memberId,
            @PathVariable("stadiumId")
                    @NotNull
                    @Positive
                    @Parameter(description = "야구장 PK", required = true)
                    Long stadiumId,
            @PathVariable("blockCode") @NotNull @Parameter(description = "블록 코드", required = true)
                    String blockCode,
            @ModelAttribute @Valid BlockReviewRequest request) {

        BlockReviewListResult result =
                readReviewUsecase.findReviewsByStadiumIdAndBlockCode(
                        memberId,
                        stadiumId,
                        blockCode,
                        request.rowNumber(),
                        request.seatNumber(),
                        request.year(),
                        request.month(),
                        request.cursor(),
                        request.sortBy(),
                        request.size());
        return BlockReviewListResponse.from(
                result, request.rowNumber(), request.seatNumber(), request.year(), request.month());
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/recentReview")
    @Operation(summary = "자신이 작성한 가장 최근 리뷰 1개를 조회한다.")
    public MyRecentReviewResponse findMyRecentReview(@Parameter(hidden = true) Long memberId) {

        MyRecentReviewResult result = readReviewUsecase.findLastReviewByMemberId(memberId);
        return MyRecentReviewResponse.from(result);
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/months")
    @Operation(summary = "리뷰가 작성된 년도와 월 정보를 조회한다.")
    public ReviewMonthsResponse findReviewMonths(
            @Parameter(hidden = true) Long memberId,
            @Parameter(description = "리뷰 타입: VIEW/FEED") ReviewType reviewType) {

        List<ReviewYearMonth> yearMonths = readReviewUsecase.findReviewMonths(memberId, reviewType);
        return ReviewMonthsResponse.from(yearMonths);
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/userInfo")
    @Operation(summary = "사용자의 리뷰 관련 정보를 조회한다.")
    public MemberInfoOnMyReviewResponse findMemberInfoOnMyReview(
            @Parameter(hidden = true) Long memberId) {
        return MemberInfoOnMyReviewResponse.from(
                readReviewUsecase.findMemberInfoOnMyReview(memberId));
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews")
    @Operation(
            summary = "자신이 작성한 리뷰 목록을 조회한다.",
            description = "연도와 월로 필터링할 수 있다. 필터링 없이 전체를 조회하려면 year와 month를 null로 입력한다.")
    public MyReviewListResponse findMyReviews(
            @Parameter(hidden = true) Long memberId,
            @ModelAttribute @Valid MyReviewRequest request) {

        MyReviewListResult result =
                readReviewUsecase.findMyReviewsByUserId(
                        memberId,
                        request.year(),
                        request.month(),
                        request.cursor(),
                        request.sortBy(),
                        request.size(),
                        request.reviewType());
        return MyReviewListResponse.from(result, request.year(), request.month());
    }

    @CurrentMember
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 id(pk)로 특정 리뷰를 조회한다.")
    public BaseReviewResponse findReviewByReviewId(
            @Parameter(hidden = true) Long memberId,
            @PathVariable("reviewId") @NotNull @Parameter(description = "리뷰 PK", required = true)
                    Long reviewId) {
        ReadReviewResult readReviewResult = readReviewUsecase.findReviewById(reviewId, memberId);

        //        // 믹스패널 이벤트(조회수) 발생
        //        applicationEventPublisher.publishEvent(
        //                new MixpanelEvent(MixpanelEventName.REVIEW_OPEN_COUNT,
        // String.valueOf(memberId)));

        return BaseReviewResponse.from(readReviewResult.review());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 id(pk)로 특정 리뷰의 조회수를 증가시킨다.")
    public void updateReviewReadCountByReviewId(
            @PathVariable("reviewId") @NotNull @Parameter(description = "리뷰 PK", required = true)
                    Long reviewId) {
        updateReviewUsecase.updateviewCount(reviewId);
    }
}
