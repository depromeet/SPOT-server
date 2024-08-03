package org.depromeet.spot.infrastructure.jpa.member.repository;

import java.util.List;

import org.depromeet.spot.common.exception.member.MemberException.InvalidLevelException;
import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.infrastructure.jpa.member.entity.LevelEntity;
import org.depromeet.spot.usecase.port.out.member.LevelRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LevelRepositoryImpl implements LevelRepository {

    private final LevelJpaRepository levelJpaRepository;

    @Override
    public Level findByValue(final int value) {
        LevelEntity entity =
                levelJpaRepository.findByValue(value).orElseThrow(InvalidLevelException::new);
        return entity.toDomain();
    }

    @Override
    public List<Level> findAll() {
        return levelJpaRepository.findAll().stream().map(LevelEntity::toDomain).toList();
    }
}
