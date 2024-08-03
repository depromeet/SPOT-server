package org.depromeet.spot.infrastructure.jpa.section.repository;

import java.util.List;

import org.depromeet.spot.infrastructure.jpa.section.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionJpaRepository extends JpaRepository<SectionEntity, Long> {

    List<SectionEntity> findAllByStadiumId(Long stadiumId);

    boolean existsByStadiumIdAndId(Long stadiumId, Long id);
}
