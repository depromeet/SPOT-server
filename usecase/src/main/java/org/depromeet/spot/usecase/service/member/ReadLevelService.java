package org.depromeet.spot.usecase.service.member;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.usecase.port.in.member.ReadLevelUsecase;
import org.depromeet.spot.usecase.port.out.member.LevelRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadLevelService implements ReadLevelUsecase {

    private final LevelRepository levelRepository;

    private static final int INITIAL_LEVEL = 0;

    @Override
    public Level findInitialLevel() {
        return levelRepository.findByValue(INITIAL_LEVEL);
    }

    @Override
    public Level findByValue(final int value) {
        return levelRepository.findByValue(value);
    }
}
