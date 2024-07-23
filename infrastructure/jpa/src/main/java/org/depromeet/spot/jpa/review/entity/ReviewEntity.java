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

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 30)
    private List<ReviewImageEntity> images;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 30)
    private List<ReviewKeywordEntity> keywords;

    public static ReviewEntity from(Review review) {
        ReviewEntity entity =
                new ReviewEntity(
                        MemberEntity.withMember(review.getMember()),
                        StadiumEntity.withStadium(review.getStadium()),
                        SectionEntity.withSection(review.getSection()),
                        BlockEntity.withBlock(review.getBlock()),
                        BlockRowEntity.withBlockRow(review.getRow()),
                        SeatEntity.withSeat(review.getSeat()),
                        review.getDateTime(),
                        review.getContent(),
                        new ArrayList<>(),
                        new ArrayList<>());

        entity.images =
                review.getImages().stream()
                        .map(image -> ReviewImageEntity.from(image, entity))
                        .collect(Collectors.toList());

        entity.keywords =
                review.getKeywords().stream()
                        .map(keyword -> ReviewKeywordEntity.from(keyword, entity))
                        .collect(Collectors.toList());

        return entity;
    }

    public Review toDomain() {
        Review review =
                Review.builder()
                        .id(this.getId())
                        .member(this.member.toDomain())
                        .stadium(this.stadium.toDomain())
                        .section(this.section.toDomain())
                        .block(this.block.toDomain())
                        .row(this.row.toDomain())
                        .seat(this.seat.toDomain())
                        .dateTime(this.dateTime)
                        .content(this.content)
                        .build();

        review.setImages(
                this.images.stream().map(ReviewImageEntity::toDomain).collect(Collectors.toList()));

        review.setKeywords(
                this.keywords.stream()
                        .map(ReviewKeywordEntity::toDomain)
                        .collect(Collectors.toList()));

        return review;
    }

    public static ReviewEntity withReview(Review review) {
        return new ReviewEntity(review);
    }

    public ReviewEntity(Review review) {
        super(review.getId(), null, null, null);
        member = MemberEntity.withMember(review.getMember());
        stadium = StadiumEntity.withStadium(review.getStadium());
        section = SectionEntity.withSection(review.getSection());
        block = BlockEntity.withBlock(review.getBlock());
        row = BlockRowEntity.withBlockRow(review.getRow());
        seat = SeatEntity.withSeat(review.getSeat());
        dateTime = review.getDateTime();
        content = review.getContent();
    }
}
