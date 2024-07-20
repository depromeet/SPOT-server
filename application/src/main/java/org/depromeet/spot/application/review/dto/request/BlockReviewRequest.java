package org.depromeet.spot.application.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.Parameter;

public record BlockReviewRequest(
        @Parameter(description = "열 번호 (필터링)") Integer rowNumber,
        @Parameter(description = "좌석 번호 (필터링)") Integer seatNumber,
        @Min(1000) @Max(9999) @Parameter(description = "년도 (4자리 숫자)") Integer year,
        @Min(1) @Max(12) @Parameter(description = "월 (1-12)") Integer month) {}
