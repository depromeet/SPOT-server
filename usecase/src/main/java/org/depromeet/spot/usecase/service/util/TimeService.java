package org.depromeet.spot.usecase.service.util;

import java.time.LocalDateTime;

import org.depromeet.spot.usecase.port.in.util.TimeUsecase;
import org.springframework.stereotype.Service;

@Service
public class TimeService implements TimeUsecase {

    @Override
    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
