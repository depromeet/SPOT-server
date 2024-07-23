package org.depromeet.spot.application.review.dto.response;

import java.util.List;

import org.depromeet.spot.domain.review.ReviewYearMonth;

public record ReviewMonthsResponse(List<ReviewYearMonth> yearMonths) {
    public static ReviewMonthsResponse from(List<ReviewYearMonth> yearMonths) {
        return new ReviewMonthsResponse(yearMonths);
    }
}
