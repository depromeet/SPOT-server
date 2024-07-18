package org.depromeet.spot.application.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import io.swagger.v3.oas.annotations.Parameter;

public record BlockReviewRequest(
        @Parameter(description = "열 ID (필터링)") Long rowId,
        @Parameter(description = "좌석 번호 (필터링)") Long seatNumber,
        @PositiveOrZero @Parameter(description = "시작 위치 (기본값: 0)") int offset,
        @Positive @Max(50) @Parameter(description = "조회할 리뷰 수 (기본값: 10, 최대: 50)") int limit) {}
