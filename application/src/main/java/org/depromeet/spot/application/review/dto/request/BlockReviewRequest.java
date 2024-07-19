package org.depromeet.spot.application.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import io.swagger.v3.oas.annotations.Parameter;

public record BlockReviewRequest(
        @Parameter(description = "열 번호 (필터링)") Long rowNumber,
        @Parameter(description = "좌석 번호 (필터링)") Long seatNumber,
        @PositiveOrZero @Parameter(description = "시작 위치 (기본값: 0)") int offset,
        @Positive @Max(50) @Parameter(description = "조회할 리뷰 수 (기본값: 10, 최대: 50)") int limit,
        @Min(1000) @Max(9999) @Parameter(description = "년도 (4자리 숫자)") Integer year,
        @Min(1) @Max(12) @Parameter(description = "월 (1-12)") Integer month) {}
