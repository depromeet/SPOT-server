package org.depromeet.spot.infrastructure.jpa.stadium.repository;

import java.util.List;

import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.infrastructure.jpa.stadium.entity.StadiumEntity;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StadiumRepositoryImpl implements StadiumRepository {

    private final StadiumJpaRepository stadiumJpaRepository;

    @Override
    public Stadium findById(final Long id) {
        return stadiumJpaRepository
                .findById(id)
                .orElseThrow(() -> new StadiumNotFoundException(id + "의 경기장은 존재하지 않습니다."))
                .toDomain();
    }

    @Override
    public List<Stadium> findAll() {
        List<StadiumEntity> entities = stadiumJpaRepository.findAll();
        return entities.stream().map(StadiumEntity::toDomain).toList();
    }

    @Override
    public Stadium save(Stadium stadium) {
        StadiumEntity entity = stadiumJpaRepository.save(StadiumEntity.from(stadium));
        return entity.toDomain();
    }

    @Override
    public boolean existsById(final Long id) {
        return stadiumJpaRepository.existsById(id);
    }
}
