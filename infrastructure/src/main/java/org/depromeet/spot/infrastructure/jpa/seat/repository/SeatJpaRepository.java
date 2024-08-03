package org.depromeet.spot.infrastructure.jpa.seat.repository;

import java.util.List;
import java.util.Optional;

import org.depromeet.spot.infrastructure.jpa.seat.entity.SeatEntity;
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

    @Query(
            "SELECT s FROM SeatEntity s "
                    + "JOIN FETCH s.stadium st "
                    + "JOIN FETCH s.section sc "
                    + "JOIN FETCH s.block b "
                    + "JOIN FETCH s.row r "
                    + "where s.block.id = :blockId and s.seatNumber = :seatNumber ")
    Optional<SeatEntity> findByIdWith(
            @Param("blockId") Long blockId, @Param("seatNumber") Integer seatNumber);

    @Query(
            "SELECT s FROM SeatEntity s "
                    + "JOIN FETCH s.stadium st "
                    + "JOIN FETCH s.section sc "
                    + "JOIN FETCH s.block b "
                    + "JOIN FETCH s.row r "
                    + "where s.block.id = :blockId")
    List<SeatEntity> findAllByBlockId(@Param("blockId") Long blockId);

    @Query(
            "SELECT s FROM SeatEntity s "
                    + "JOIN FETCH s.stadium st "
                    + "JOIN FETCH s.section sc "
                    + "JOIN FETCH s.block b "
                    + "JOIN FETCH s.row r "
                    + "where s.section.id = :sectionId")
    List<SeatEntity> findAllBySectionId(@Param("sectionId") Long sectionId);
}
