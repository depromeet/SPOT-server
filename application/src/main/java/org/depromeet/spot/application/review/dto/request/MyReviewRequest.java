package org.depromeet.spot.application.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.Parameter;

public record MyReviewRequest(
        @Parameter(description = "유저 ID") Long userId,
        @Min(1000) @Max(9999) @Parameter(description = "년도 (4자리 숫자)") Integer year,
        @Min(1) @Max(12) @Parameter(description = "월 (1-12)") Integer month) {}
