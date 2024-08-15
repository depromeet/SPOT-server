package org.depromeet.spot.usecase.service.level;

import java.util.List;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.usecase.port.in.member.level.ReadLevelUsecase;
import org.depromeet.spot.usecase.port.out.member.LevelRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadLevelService implements ReadLevelUsecase {

    private final LevelRepository levelRepository;

    private static final int INITIAL_LEVEL = 0;

    @Override
    @Cacheable(cacheNames = {"levelsCache"})
    public List<Level> findAllLevels() {
        return levelRepository.findAll();
    }

    @Override
    public Level findLevelUpDialogInfo(final int nextLevel) {
        return findByValue(nextLevel);
    }

    @Override
    public Level findInitialLevel() {
        return levelRepository.findByValue(INITIAL_LEVEL);
    }

    @Override
    @Cacheable(
            cacheNames = {"levelValuesCache"},
            key = "#value")
    public Level findByValue(final int value) {
        return levelRepository.findByValue(value);
    }
}
