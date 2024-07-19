package org.depromeet.spot.domain.review;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Review {

    private final Long id;
    private final Long userId;
    private final Long stadiumId;
    private final Long blockId;
    private final Long seatId;
    private final Long rowId;

    private final LocalDateTime dateTime;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final List<ReviewImage> images;
    private final List<ReviewKeyword> keywords;

    public Review addImagesAndKeywords(
            List<ReviewImage> newImages, List<ReviewKeyword> newKeywords) {
        return new Review(
                id,
                userId,
                stadiumId,
                blockId,
                seatId,
                rowId,
                dateTime,
                content,
                createdAt,
                updatedAt,
                deletedAt,
                newImages,
                newKeywords);
    }
}
