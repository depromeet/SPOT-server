package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.review.ReviewException.InvalidReviewLikesException;
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

    public enum ReviewType {
        VIEW, // 시야 후기
        FEED, // 직관 후기
    }

    public enum SortCriteria {
        DATE_TIME,
        LIKES_COUNT,
    }

    private final Long id;
    private final Member member;
    private final Stadium stadium;
    private final Section section;
    private final Block block;
    private final BlockRow row;
    private final Seat seat;
    private final LocalDateTime dateTime;
    private final String content;
    private LocalDateTime deletedAt;
    private List<ReviewImage> images;
    private List<ReviewKeyword> keywords;
    private transient Map<Long, Keyword> keywordMap;
    private int likesCount;
    private int scrapsCount;
    private final ReviewType reviewType;
    private long viewsCount;

    public static final int DEFAULT_LIKE_COUNT = 0;
    public static final int DEFAULT_SCRAPS_COUNT = 0;

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
            List<ReviewKeyword> keywords,
            int likesCount,
            int scrapsCount,
            ReviewType reviewType,
            long viewsCount) {
        if (likesCount < 0) {
            throw new InvalidReviewLikesException();
        }

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
        this.likesCount = likesCount;
        this.scrapsCount = scrapsCount;
        this.reviewType = reviewType;
        this.viewsCount = viewsCount;
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

    public void addLike() {
        this.likesCount++;
    }

    public void cancelLike() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }

    public void addScrap() {
        this.scrapsCount++;
    }

    public void cancelScrap() {
        if (this.scrapsCount > 0) {
            this.scrapsCount--;
        }
    }

    public void addViews() {
        this.viewsCount++;
    }

    public void setDeletedAt(LocalDateTime now) {
        this.deletedAt = now;
    }

    public Review withLimitedImages(int limit) {
        List<ReviewImage> limitedImages =
                this.images.stream().limit(limit).collect(Collectors.toList());
        return new Review(
                this.id,
                this.member,
                this.stadium,
                this.section,
                this.block,
                this.row,
                this.seat,
                this.dateTime,
                this.content,
                this.deletedAt,
                limitedImages,
                this.keywords,
                this.likesCount,
                this.scrapsCount,
                this.reviewType,
                this.viewsCount);
    }
}
