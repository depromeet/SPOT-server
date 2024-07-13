package org.depromeet.spot.jpa.section.repository;

import java.util.List;

import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.jpa.section.entity.SectionEntity;
import org.depromeet.spot.usecase.port.out.section.SectionRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SectionRepositoryImpl implements SectionRepository {

    private final SectionJpaRepository sectionJpaRepository;

    @Override
    public List<Section> findAllByStadium(final Long stadiumId) {
        List<SectionEntity> entities = sectionJpaRepository.findAllByStadiumId(stadiumId);
        return entities.stream().map(SectionEntity::toDomain).toList();
    }

    @Override
    public Section save(Section section) {
        // TODO: test를 위해 추가 -> 구역 생성 티켓 작업할 때 구현 예정
        return null;
    }
}
