package org.depromeet.spot.usecase.port.in.member;

import org.depromeet.spot.domain.member.Level;

public interface ReadLevelUsecase {

    Level findInitialLevel();

    Level findByValue(int value);
}
