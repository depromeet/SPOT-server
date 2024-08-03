package org.depromeet.spot.infrastructure.jpa.section.repository;

import java.util.List;

import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.infrastructure.jpa.section.entity.SectionEntity;
import org.depromeet.spot.usecase.port.out.section.SectionRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SectionRepositoryImpl implements SectionRepository {

    private final SectionJpaRepository sectionJpaRepository;
    private final SectionJdbcRepository sectionJdbcRepository;

    @Override
    public List<Section> findAllByStadium(final Long stadiumId) {
        List<SectionEntity> entities = sectionJpaRepository.findAllByStadiumId(stadiumId);
        return entities.stream().map(SectionEntity::toDomain).toList();
    }

    @Override
    public Section save(Section section) {
        SectionEntity entity = sectionJpaRepository.save(SectionEntity.from(section));
        return entity.toDomain();
    }

    @Override
    public void saveAll(List<Section> sections) {
        sectionJdbcRepository.createSections(sections);
    }

    @Override
    public boolean existsInStadium(Long stadiumId, Long sectionId) {
        return sectionJpaRepository.existsByStadiumIdAndId(stadiumId, sectionId);
    }
}
