package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Review {
    private final Long id;
    private final Member member;
    private final Stadium stadium;
    private final Section section;
    private final Block block;
    private final BlockRow row;
    private final Seat seat;
    private final LocalDateTime dateTime;
    private final String content;
    private final LocalDateTime deletedAt;
    private final List<ReviewImage> images;
    private final List<ReviewKeyword> keywords;

    @Builder
    public Review(
            Long id,
            Member member,
            Stadium stadium,
            Section section,
            Block block,
            BlockRow row,
            Seat seat,
            LocalDateTime dateTime,
            String content,
            LocalDateTime deletedAt,
            List<ReviewImage> images,
            List<ReviewKeyword> keywords) {
        this.id = id;
        this.member = member;
        this.stadium = stadium;
        this.section = section;
        this.block = block;
        this.row = row;
        this.seat = seat;
        this.dateTime = dateTime;
        this.content = content;
        this.deletedAt = deletedAt;
        this.images = images != null ? images : new ArrayList<>();
        this.keywords = keywords != null ? keywords : new ArrayList<>();
    }

    public Review addImagesAndKeywords(
            List<ReviewImage> newImages, List<ReviewKeyword> newKeywords) {
        List<ReviewImage> updatedImages = new ArrayList<>(this.images);
        updatedImages.addAll(newImages);

        List<ReviewKeyword> updatedKeywords = new ArrayList<>(this.keywords);
        updatedKeywords.addAll(newKeywords);

        return Review.builder()
                .id(this.id)
                .member(this.member)
                .stadium(this.stadium)
                .section(this.section)
                .block(this.block)
                .row(this.row)
                .seat(this.seat)
                .dateTime(this.dateTime)
                .content(this.content)
                .deletedAt(this.deletedAt)
                .images(updatedImages)
                .keywords(updatedKeywords)
                .build();
    }
}
