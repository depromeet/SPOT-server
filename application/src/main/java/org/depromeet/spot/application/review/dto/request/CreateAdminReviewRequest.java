package org.depromeet.spot.application.review.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewDateTimeFormatException;
import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewKeywordsException;

public record CreateAdminReviewRequest(
        @NotNull int rowNumber,
        Integer seatNumber,
        List<String> good,
        List<String> bad,
        @NotEmpty String content,
        @NotNull String dateTime) {

    private void checkHasKeyword() {
        if ((good == null || good.isEmpty()) && (bad == null || bad.isEmpty())) {
            throw new InvalidReviewKeywordsException();
        }
    }

    private LocalDateTime toLocalDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidReviewDateTimeFormatException();
        }
    }
}
