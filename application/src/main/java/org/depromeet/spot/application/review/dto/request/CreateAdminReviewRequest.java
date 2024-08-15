package org.depromeet.spot.application.review.dto.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewDateTimeFormatException;
import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewKeywordsException;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateAdminReviewCommand;
import org.springframework.web.multipart.MultipartFile;

public record CreateAdminReviewRequest(
        Integer seatNumber,
        List<String> good,
        List<String> bad,
        @NotBlank String content,
        @NotNull String dateTime) {

    public CreateAdminReviewCommand toCommand(List<MultipartFile> images) {
        checkHasKeyword();
        return CreateAdminReviewCommand.builder()
                .bad(bad)
                .good(good)
                .images(images)
                .content(content)
                .seatNumber(seatNumber)
                .dateTime(toLocalDateTime(dateTime))
                .build();
    }

    private void checkHasKeyword() {
        if ((this.good == null || good.isEmpty()) && (bad == null || bad.isEmpty())) {
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
