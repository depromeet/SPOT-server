package org.depromeet.spot.application.review.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewDateTimeFormatException;
import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewKeywordsException;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase.UpdateReviewCommand;

public record UpdateReviewRequest(
        @NotNull Long blockId,
        @NotNull Integer seatNumber,
        @Size(min = 1, max = 3) List<String> images,
        List<String> good,
        List<String> bad,
        String content,
        @NotNull String dateTime) {

    public UpdateReviewCommand toCommand() {
        validateGoodAndBad();
        return UpdateReviewCommand.builder()
                .blockId(blockId)
                .seatNumber(seatNumber)
                .images(images)
                .good(good)
                .bad(bad)
                .content(content)
                .dateTime(toLocalDateTime(dateTime))
                .build();
    }

    private void validateGoodAndBad() {
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