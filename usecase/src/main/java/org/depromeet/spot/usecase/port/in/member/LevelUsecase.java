package org.depromeet.spot.usecase.port.in.member;

import java.util.List;

import org.depromeet.spot.domain.member.Level;

public interface LevelUsecase {
    List<Level> findAllLevels();
}
