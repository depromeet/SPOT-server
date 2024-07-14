package org.depromeet.spot.application.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.ReviewListResult;

public record ReviewListResponse(
        List<KeywordCountResponse> keywords,
        List<ReviewResponse> reviews,
        int totalCount,
        int filteredCount,
        int offset,
        int limit,
        boolean hasMore,
        FilterInfo filter) {
    public static ReviewListResponse from(ReviewListResult result) {
        List<ReviewResponse> reviewResponses =
                result.reviews().stream().map(ReviewResponse::from).collect(Collectors.toList());

        List<KeywordCountResponse> keywordResponses =
                result.topKeywords().stream()
                        .map(kc -> new KeywordCountResponse(kc.content(), kc.count()))
                        .collect(Collectors.toList());

        boolean hasMore = (result.offset() + result.reviews().size()) < result.totalCount();
        FilterInfo filter = new FilterInfo(null, null); // Assuming no filter info for now

        return new ReviewListResponse(
                keywordResponses,
                reviewResponses,
                result.totalCount(),
                result.reviews().size(),
                result.offset(),
                result.limit(),
                hasMore,
                filter);
    }

    record KeywordCountResponse(String content, Long count) {}

    record FilterInfo(Long rowId, Integer seatNumber) {}
}
