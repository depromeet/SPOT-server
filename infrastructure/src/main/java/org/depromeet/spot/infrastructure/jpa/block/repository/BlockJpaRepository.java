package org.depromeet.spot.infrastructure.jpa.block.repository;

import java.util.List;
import java.util.Optional;

import org.depromeet.spot.infrastructure.jpa.block.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlockJpaRepository extends JpaRepository<BlockEntity, Long> {
    Optional<BlockEntity> findByStadiumIdAndCode(Long stadiumId, String code);

    @Query("SELECT b FROM BlockEntity b where b.sectionId = :sectionId order by b.code asc")
    List<BlockEntity> findAllBySectionId(@Param("sectionId") Long sectionId);

    boolean existsByStadiumIdAndCode(Long stadiumId, String code);
}
