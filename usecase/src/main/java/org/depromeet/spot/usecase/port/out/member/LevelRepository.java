package org.depromeet.spot.usecase.port.out.member;

import java.util.List;

import org.depromeet.spot.domain.member.Level;

public interface LevelRepository {

    Level findByValue(int value);

    List<Level> findAll();
}
