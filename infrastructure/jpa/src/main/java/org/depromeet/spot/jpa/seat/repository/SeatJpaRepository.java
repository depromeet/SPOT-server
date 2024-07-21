package org.depromeet.spot.jpa.seat.repository;

import java.util.List;

import org.depromeet.spot.jpa.seat.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {

    List<SeatEntity> findAllByBlockId(Long blockId);

    List<SeatEntity> findAllBySectionId(Long sectionId);
}
