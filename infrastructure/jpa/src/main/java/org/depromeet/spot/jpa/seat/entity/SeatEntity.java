package org.depromeet.spot.jpa.seat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.seat.Seat;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@NoArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(name = "section_id", nullable = false)
    private Long sectionId;

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "row_id", nullable = false)
    private Long rowId;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    public SeatEntity(
            Long id, Long stadiumId, Long sectionId, Long blockId, Long rowId, Integer seatNumber) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.sectionId = sectionId;
        this.blockId = blockId;
        this.rowId = rowId;
        this.seatNumber = seatNumber;
    }

    public static SeatEntity from(Seat seat) {
        return new SeatEntity(
                seat.getId(),
                seat.getStadiumId(),
                seat.getSectionId(),
                seat.getBlockId(),
                seat.getRowId(),
                seat.getSeatNumber());
    }

    public Seat toDomain() {
        return new Seat(id, stadiumId, sectionId, blockId, rowId, seatNumber);
    }
}
