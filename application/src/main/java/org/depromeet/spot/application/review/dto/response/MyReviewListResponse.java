package org.depromeet.spot.application.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.application.review.dto.response.BlockReviewListResponse.FilterInfo;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.MemberInfoOnMyReviewResult;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.MyReviewListResult;

public record MyReviewListResponse(
        MemberInfoOnMyReviewResult memberInfoOnMyReview,
        List<MyReviewResponse> reviews,
        long totalElements,
        Long nextCursor,
        boolean hasNext,
        FilterInfo filter) {
    public static MyReviewListResponse from(
            MyReviewListResult result, Integer year, Integer month) {

        List<MyReviewResponse> reviews =
                result.reviews().stream().map(MyReviewResponse::from).collect(Collectors.toList());
        FilterInfo filter = new FilterInfo(null, null, year, month);

        return new MyReviewListResponse(
                result.memberInfoOnMyReviewResult(),
                reviews,
                result.totalElements(),
                result.nextCursor(),
                result.hasNext(),
                filter);
    }

    public record MyReviewResponse(
            BaseReviewResponse baseReview,
            String stadiumName,
            String sectionName,
            String blockCode) {
        public static MyReviewResponse from(Review review) {
            return new MyReviewResponse(
                    BaseReviewResponse.from(review),
                    review.getStadium().getName(),
                    review.getSection().getName(),
                    review.getBlock().getCode());
        }
    }
}
