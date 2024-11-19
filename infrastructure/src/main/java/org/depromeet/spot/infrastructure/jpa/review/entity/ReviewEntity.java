package org.depromeet.spot.infrastructure.jpa.review.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.infrastructure.jpa.block.entity.BlockEntity;
import org.depromeet.spot.infrastructure.jpa.block.entity.BlockRowEntity;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;
import org.depromeet.spot.infrastructure.jpa.member.entity.MemberEntity;
import org.depromeet.spot.infrastructure.jpa.review.entity.image.ReviewImageEntity;
import org.depromeet.spot.infrastructure.jpa.review.entity.keyword.ReviewKeywordEntity;
import org.depromeet.spot.infrastructure.jpa.seat.entity.SeatEntity;
import org.depromeet.spot.infrastructure.jpa.section.entity.SectionEntity;
import org.depromeet.spot.infrastructure.jpa.stadium.entity.StadiumEntity;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

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
    @JoinColumn(name = "seat_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
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

    @ColumnDefault("0")
    @Column(name = "likes_count")
    private Integer likesCount;

    @ColumnDefault("0")
    @Column(name = "scraps_count")
    private Integer scrapsCount;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'VIEW'")
    @Column(name = "review_type", nullable = false)
    private ReviewType reviewType;

    @Version private Long version;

    public void incrementLikesCount() {
        this.likesCount++;
    }

    public void decrementLikesCount() {
        this.likesCount--;
    }

    public static ReviewEntity from(Review review) {
        SeatEntity seatEntity =
                review.getSeat() != null ? SeatEntity.withSeat(review.getSeat()) : null;
        BlockRowEntity blockRowEntity =
                review.getRow() != null ? BlockRowEntity.withBlockRow(review.getRow()) : null;

        ReviewEntity entity =
                new ReviewEntity(
                        MemberEntity.withMember(review.getMember()),
                        StadiumEntity.withStadium(review.getStadium()),
                        SectionEntity.withSection(review.getSection()),
                        BlockEntity.withBlock(review.getBlock()),
                        blockRowEntity,
                        seatEntity,
                        review.getDateTime(),
                        review.getContent(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        review.getLikesCount(),
                        review.getScrapsCount(),
                        review.getReviewType(),
                        review.getVersion());

        entity.setId(review.getId()); // ID 설정 추가

        entity.images =
                review.getImages().stream()
                        .map(image -> ReviewImageEntity.from(image, entity))
                        .toList();

        entity.keywords =
                review.getKeywords().stream()
                        .map(keyword -> ReviewKeywordEntity.from(keyword, entity))
                        .toList();

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
                        .row((this.row == null) ? null : this.row.toDomain())
                        .seat((this.seat == null) ? null : this.seat.toDomain())
                        .dateTime(this.dateTime)
                        .content(this.content)
                        .likesCount(likesCount)
                        .scrapsCount(scrapsCount)
                        .reviewType(reviewType)
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
        likesCount = review.getLikesCount();
        scrapsCount = review.getScrapsCount();
        reviewType = review.getReviewType();
    }
}
