package org.depromeet.spot.application.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.MyReviewListResult;

public record MyReviewListResponse(
        List<BaseReviewResponse> reviews, long totalCount, int offset, int limit, boolean hasMore) {
    public static MyReviewListResponse from(MyReviewListResult result) {
        List<BaseReviewResponse> reviewResponses =
                result.reviews().stream()
                        .map(
                                review ->
                                        BaseReviewResponse.from(
                                                review,
                                                result.getMemberByReviewId(review.getId()),
                                                result.getSeatByReviewId(review.getId())))
                        .collect(Collectors.toList());

        boolean hasMore = (result.offset() + result.reviews().size()) < result.totalCount();

        return new MyReviewListResponse(
                reviewResponses, result.totalCount(), result.offset(), result.limit(), hasMore);
    }
}
