package org.depromeet.spot.application.review.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewDateTimeFormatException;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateReviewCommand;

public record CreateReviewRequest(
        @NotNull List<String> images,
        List<String> good,
        List<String> bad,
        @NotNull String content,
        @NotNull String dateTime) {

    public CreateReviewCommand toCommand() {
        validateGoodAndBad();
        return CreateReviewCommand.builder()
                .images(images)
                .good(good)
                .bad(bad)
                .content(content)
                .dateTime(toLocalDateTime(dateTime))
                .build();
    }

    private void validateGoodAndBad() {
        if ((good == null || good.isEmpty()) && (bad == null || bad.isEmpty())) {
            throw new IllegalArgumentException("At least one of 'good' or 'bad' must be provided");
        }
    }

    private LocalDateTime toLocalDateTime(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            // 시간 정보가 없으므로 자정(00:00)으로 설정
            return LocalDateTime.of(date, LocalTime.MIDNIGHT);
        } catch (DateTimeParseException e) {
            throw new InvalidReviewDateTimeFormatException();
        }
    }
}
