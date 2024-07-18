package org.depromeet.spot.application.review.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewDateTimeFormatException;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateReviewCommand;

public record CreateReviewRequest(
        List<String> images, List<String> good, List<String> bad, String content, String dateTime) {

    public CreateReviewCommand toCommand() {
        return CreateReviewCommand.builder()
                .images(images)
                .good(good)
                .bad(bad)
                .content(content)
                .dateTime(toLocalDateTime(dateTime))
                .build();
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
