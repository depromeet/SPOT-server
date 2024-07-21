package org.depromeet.spot.jpa.review.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.jpa.block.entity.BlockRowEntity;
import org.depromeet.spot.jpa.common.entity.BaseEntity;
import org.depromeet.spot.jpa.member.entity.MemberEntity;
import org.depromeet.spot.jpa.review.entity.image.ReviewImageEntity;
import org.depromeet.spot.jpa.review.entity.keyword.ReviewKeywordEntity;
import org.depromeet.spot.jpa.seat.entity.SeatEntity;
import org.depromeet.spot.jpa.section.entity.SectionEntity;
import org.depromeet.spot.jpa.stadium.entity.StadiumEntity;
import org.hibernate.annotations.BatchSize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "stadium_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StadiumEntity stadium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "section_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SectionEntity section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "block_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlockEntity block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "row_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlockRowEntity row;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "seat_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SeatEntity seat;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 30)
    private List<ReviewImageEntity> images;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 30)
    private List<ReviewKeywordEntity> keywords;

    public static ReviewEntity from(Review review) {
        return new ReviewEntity(
                MemberEntity.from(review.getMember()),
                StadiumEntity.from(review.getStadium()),
                SectionEntity.from(review.getSection()),
                BlockEntity.from(review.getBlock()),
                BlockRowEntity.from(review.getRow()),
                SeatEntity.from(review.getSeat()),
                review.getDateTime(),
                review.getContent(),
                review.getDeletedAt(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public Review toDomain() {
        return Review.builder()
                .id(this.getId())
                .member(member.toDomain())
                .stadium(stadium.toDomain())
                .section(section.toDomain())
                .block(block.toDomain())
                .row(row.toDomain())
                .seat(seat.toDomain())
                .dateTime(dateTime)
                .content(content)
                .deletedAt(deletedAt)
                .images(
                        images.stream()
                                .map(ReviewImageEntity::toDomain)
                                .collect(Collectors.toList()))
                .keywords(
                        keywords.stream()
                                .map(ReviewKeywordEntity::toDomain)
                                .collect(Collectors.toList()))
                .build();
    }
}
