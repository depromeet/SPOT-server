package org.depromeet.spot.usecase.service.level;

import java.util.List;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.usecase.port.in.member.LevelUsecase;
import org.depromeet.spot.usecase.port.in.member.ReadLevelUsecase;
import org.depromeet.spot.usecase.port.out.member.LevelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LevelService implements LevelUsecase {

    private final LevelRepository levelRepository;
    private final ReadLevelUsecase readLevelUsecase;

    @Override
    public List<Level> findAllLevels() {
        return levelRepository.findAll();
    }

    @Override
    public Level findLevelUpDialogInfo(final int nextLevel) {
        return readLevelUsecase.findByValue(nextLevel);
    }
}
