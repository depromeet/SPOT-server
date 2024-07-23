package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
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
    private List<ReviewImage> images;
    private List<ReviewKeyword> keywords;
    private transient Map<Long, Keyword> keywordMap;

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

    public void addKeyword(ReviewKeyword keyword) {
        if (this.keywords == null) {
            this.keywords = new ArrayList<>();
        }
        this.keywords.add(keyword);
    }

    public void addImage(ReviewImage image) {
        this.images.add(image);
    }

    public void setImages(List<ReviewImage> images) {
        this.images = images;
    }

    public void setKeywords(List<ReviewKeyword> keywords) {
        this.keywords = keywords;
    }

    public void setKeywordMap(Map<Long, Keyword> keywordMap) {
        this.keywordMap = keywordMap;
    }

    public Keyword getKeywordById(Long keywordId) {
        return keywordMap != null ? keywordMap.get(keywordId) : null;
    }
}
