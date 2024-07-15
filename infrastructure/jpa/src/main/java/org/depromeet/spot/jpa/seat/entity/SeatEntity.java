package org.depromeet.spot.jpa.seat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity extends BaseEntity {

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

    public static SeatEntity from(Seat seat) {
        return new SeatEntity(
                seat.getStadiumId(),
                seat.getSectionId(),
                seat.getBlockId(),
                seat.getRowId(),
                seat.getSeatNumber());
    }

    public Seat toDomain() {
        return new Seat(this.getId(), stadiumId, sectionId, blockId, rowId, seatNumber);
    }
}
