package org.depromeet.spot.jpa.review.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.Review;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "block_id", nullable = false)
    private Long blockId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "row_id", nullable = false)
    private Long rowId;

    @Column(name = "seat_number", nullable = false)
    private Long seatNumber;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    public ReviewEntity(
            Long id,
            Long userId,
            Long blockId,
            Long seatId,
            Long rowId,
            Long seatNumber,
            LocalDate date,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String status) {
        this.id = id;
        this.userId = userId;
        this.blockId = blockId;
        this.seatId = seatId;
        this.rowId = rowId;
        this.seatNumber = seatNumber;
        this.date = date;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public static ReviewEntity from(Review review) {
        return new ReviewEntity(
                review.getId(),
                review.getUserId(),
                review.getBlockId(),
                review.getSeatId(),
                review.getRowId(),
                review.getSeatNumber(),
                review.getDate(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.getStatus());
    }

    public Review toDomain() {
        return new Review(
                id,
                userId,
                blockId,
                seatId,
                rowId,
                seatNumber,
                date,
                content,
                createdAt,
                updatedAt,
                status);
    }
}
