package org.depromeet.spot.jpa.stadium.repository;

import java.util.List;

import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.jpa.stadium.entity.StadiumEntity;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StadiumRepositoryImpl implements StadiumRepository {

    private final StadiumJpaRepository stadiumJpaRepository;

    @Override
    public Stadium findById(final Long id) {
        // FIXME: custom exception 추가
        StadiumEntity entity =
                stadiumJpaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return entity.toDomain();
    }

    @Override
    public List<Stadium> findAll() {
        List<StadiumEntity> entities = stadiumJpaRepository.findAll();
        return entities.stream().map(StadiumEntity::toDomain).toList();
    }
}
