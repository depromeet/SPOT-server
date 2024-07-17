package org.depromeet.spot.ncp.mock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.depromeet.spot.usecase.port.in.util.TimeUsecase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakeTimeUsecase implements TimeUsecase {

    private final String dateTimeStr;

    @Override
    public LocalDateTime getNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
