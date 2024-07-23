package org.depromeet.spot.application.review;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.depromeet.spot.application.review.dto.request.BlockReviewRequest;
import org.depromeet.spot.application.review.dto.request.MyReviewRequest;
import org.depromeet.spot.application.review.dto.response.BlockReviewListResponse;
import org.depromeet.spot.application.review.dto.response.MyReviewListResponse;
import org.depromeet.spot.application.review.dto.response.ReviewMonthsResponse;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
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
    private final JwtTokenUtil jwtTokenUtil;

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
            @ParameterObject @PageableDefault(size = 20, page = 0) Pageable pageable) {

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/months")
    @Operation(summary = "리뷰가 작성된 년도와 월 정보를 조회한다.")
    public ReviewMonthsResponse findReviewMonths(
            //        @RequestHeader("Authorization") String token
            @RequestParam @Parameter(description = "유저 ID") Long userId) {

        //        // "Bearer " 접두사 제거
        //        String jwt = token.replace("Bearer ", "");
        //
        //        // ToDo: JWT 유효성 검사
        //        //        if (!jwtTokenUtil.isValidateToken(jwt)) {
        //        //            throw new UnauthorizedException("Invalid token");
        //        //        }
        //
        //        // JWT에서 id 추출
        //        String memberId = jwtTokenUtil.getIdFromJWT(jwt);
        //
        //        // memberId를 Long으로 변환
        //        Long memberIdLong = Long.parseLong(memberId);

        List<ReviewYearMonth> yearMonths = readReviewUsecase.findReviewMonths(userId);
        return ReviewMonthsResponse.from(yearMonths);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews")
    @Operation(
            summary = "자신이 작성한 리뷰 목록을 조회한다.",
            description = "연도와 월로 필터링할 수 있다. 필터링 없이 전체를 조회하려면 year와 month를 null로 입력한다.")
    public MyReviewListResponse findMyReviews(
            @RequestParam @Parameter(description = "유저 ID") Long userId,
            @ModelAttribute @Valid MyReviewRequest request,
            @ParameterObject @PageableDefault(size = 20, page = 0) Pageable pageable) {

        ReadReviewUsecase.MyReviewListResult result =
                readReviewUsecase.findMyReviewsByUserId(
                        userId, request.year(), request.month(), pageable);
        return MyReviewListResponse.from(result, request.year(), request.month());
    }
}
