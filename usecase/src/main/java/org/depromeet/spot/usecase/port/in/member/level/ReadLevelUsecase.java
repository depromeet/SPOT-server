package org.depromeet.spot.usecase.port.in.member.level;

import java.util.List;

import org.depromeet.spot.domain.member.Level;

public interface ReadLevelUsecase {

    Level findInitialLevel();

    Level findByValue(int value);

    List<Level> findAllLevels();

    Level findLevelUpDialogInfo(int nextLevel);
}
