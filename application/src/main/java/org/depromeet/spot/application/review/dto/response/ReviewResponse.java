package org.depromeet.spot.application.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.domain.review.ReviewKeyword;

public record ReviewResponse(
        Long id,
        Long userId,
        Long blockId,
        Long seatId,
        Long rowId,
        Long seatNumber,
        LocalDateTime date,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ImageResponse> images,
        List<KeywordResponse> keywords) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUserId(),
                review.getBlockId(),
                review.getSeatId(),
                review.getRowId(),
                review.getSeatNumber(),
                review.getDateTime(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                mapToImageResponses(review.getImages()),
                mapToKeywordResponses(review.getKeywords()));
    }

    private static List<ImageResponse> mapToImageResponses(List<ReviewImage> images) {
        return images.stream()
                .map(image -> new ImageResponse(image.getId(), image.getUrl()))
                .collect(Collectors.toList());
    }

    private static List<KeywordResponse> mapToKeywordResponses(List<ReviewKeyword> keywords) {
        return keywords.stream()
                .map(
                        keyword ->
                                new KeywordResponse(
                                        keyword.getId(),
                                        keyword.getKeywordId(),
                                        keyword.getIsPositive()))
                .collect(Collectors.toList());
    }

    record ImageResponse(Long id, String url) {}

    record KeywordResponse(Long id, Long keywordId, Boolean isPositive) {}
}
