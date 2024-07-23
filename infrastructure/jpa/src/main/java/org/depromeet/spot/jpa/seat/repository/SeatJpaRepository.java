package org.depromeet.spot.jpa.seat.repository;

import java.util.List;
import java.util.Optional;

import org.depromeet.spot.jpa.seat.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {

    @Query(
            "SELECT s FROM SeatEntity s "
                    + "JOIN FETCH s.stadium st "
                    + "JOIN FETCH s.section sc "
                    + "JOIN FETCH s.block b "
                    + "JOIN FETCH s.row r "
                    + "where s.id = :id ")
    Optional<SeatEntity> findByIdWith(@Param("id") Long id);

    List<SeatEntity> findAllByBlockId(Long blockId);

    List<SeatEntity> findAllBySectionId(Long sectionId);
}
