package org.depromeet.spot.infrastructure.jpa.seat.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.infrastructure.jpa.block.entity.BlockEntity;
import org.depromeet.spot.infrastructure.jpa.block.entity.BlockRowEntity;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;
import org.depromeet.spot.infrastructure.jpa.section.entity.SectionEntity;
import org.depromeet.spot.infrastructure.jpa.stadium.entity.StadiumEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "stadium_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StadiumEntity stadium;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "section_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SectionEntity section;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "block_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlockEntity block;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "row_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlockRowEntity row;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    public static SeatEntity from(Seat seat) {
        return new SeatEntity(
                StadiumEntity.from(seat.getStadium()),
                SectionEntity.from(seat.getSection()),
                BlockEntity.from(seat.getBlock()),
                BlockRowEntity.from(seat.getRow()),
                seat.getSeatNumber());
    }

    public static SeatEntity withSeat(Seat seat) {
        return new SeatEntity(seat);
    }

    public SeatEntity(Seat seat) {
        super(seat.getId(), null, null, null);
        stadium = StadiumEntity.withStadium(seat.getStadium());
        section = SectionEntity.withSection(seat.getSection());
        block = BlockEntity.withBlock(seat.getBlock());
        row = BlockRowEntity.withBlockRow(seat.getRow());
        seatNumber = seat.getSeatNumber();
    }

    public Seat toDomain() {
        return new Seat(
                this.getId(),
                stadium.toDomain(),
                section.toDomain(),
                block.toDomain(),
                row.toDomain(),
                seatNumber);
    }
}
