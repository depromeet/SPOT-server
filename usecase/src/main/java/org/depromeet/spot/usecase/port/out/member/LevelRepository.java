package org.depromeet.spot.usecase.port.out.member;

import org.depromeet.spot.domain.member.Level;

public interface LevelRepository {

    Level findByValue(int value);
}
