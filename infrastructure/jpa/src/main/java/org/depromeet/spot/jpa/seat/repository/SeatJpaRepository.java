package org.depromeet.spot.jpa.seat.repository;

import org.depromeet.spot.jpa.seat.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {}
