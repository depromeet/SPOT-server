package org.depromeet.spot.application.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.BlockReviewListResult;

public record BlockReviewListResponse(
        List<KeywordCountResponse> keywords,
        List<BaseReviewResponse> reviews,
        Long totalCount,
        int filteredCount,
        int offset,
        int limit,
        boolean hasMore,
        FilterInfo filter) {
    public static BlockReviewListResponse from(
            BlockReviewListResult result, Long rowNumber, Long seatNumber) {
        List<BaseReviewResponse> reviewResponses =
                result.reviews().stream()
                        .map(
                                review ->
                                        BaseReviewResponse.from(
                                                review,
                                                result.getMemberByReviewId(review.getId()),
                                                result.getSeatByReviewId(review.getId())))
                        .collect(Collectors.toList());

        List<KeywordCountResponse> keywordResponses =
                result.topKeywords().stream()
                        .map(kc -> new KeywordCountResponse(kc.content(), kc.count()))
                        .collect(Collectors.toList());

        boolean hasMore = (result.offset() + result.reviews().size()) < result.totalCount();
        FilterInfo filter = new FilterInfo(rowNumber, seatNumber);

        return new BlockReviewListResponse(
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

    record FilterInfo(Long rowNumber, Long seatNumber) {}
}
