package org.depromeet.spot.application.review.dto.request.scrap;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase.MyScrapCommand;

public record MyScrapRequest(
        @NotNull @Positive Long stadiumId,
        List<Integer> months,
        List<String> good,
        List<String> bad) {
    public MyScrapRequest {
        if (months == null) {
            months = List.of();
        }
        if (good == null) {
            good = List.of();
        }
        if (bad == null) {
            bad = List.of();
        }
    }

    public MyScrapCommand toCommand() {
        return MyScrapCommand.builder()
                .stadiumId(stadiumId)
                .months(months)
                .good(good)
                .bad(bad)
                .build();
    }
}
