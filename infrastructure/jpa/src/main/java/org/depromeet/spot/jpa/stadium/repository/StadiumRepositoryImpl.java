package org.depromeet.spot.jpa.stadium.repository;

import java.util.List;

import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
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
        // TODO: test를 위해 추가 -> 구장 저장 API 티켓때 구현 예정
        return null;
    }
}
