package org.depromeet.spot.application.review.dto.response;

import java.util.List;

import org.depromeet.spot.domain.review.image.TopReviewImage;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockKeywordInfo;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockReviewListResult;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.LocationInfo;

public record BlockReviewListResponse(
        LocationInfo location,
        List<KeywordCountResponse> keywords,
        List<BaseReviewResponse> reviews,
        List<TopReviewImageResponse> topReviewImages,
        Long totalElements,
        String nextCursor,
        boolean hasNext,
        FilterInfo filter) {

    public static BlockReviewListResponse from(
            BlockReviewListResult result,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month) {

        List<BaseReviewResponse> reviewResponses =
                result.reviews().stream().map(BaseReviewResponse::from).toList();

        List<KeywordCountResponse> keywordResponses =
                result.topKeywords().stream().map(KeywordCountResponse::from).toList();

        List<TopReviewImageResponse> topReviewImageResponses =
                result.topReviewImages().stream().map(TopReviewImageResponse::from).toList();

        FilterInfo filter = new FilterInfo(rowNumber, seatNumber, year, month);

        return new BlockReviewListResponse(
                result.location(),
                keywordResponses,
                reviewResponses,
                topReviewImageResponses,
                result.totalElements(),
                result.nextCursor(),
                result.hasNext(),
                filter);
    }

    public record KeywordCountResponse(String content, Long count, Boolean isPositive) {

        public static KeywordCountResponse from(BlockKeywordInfo info) {
            return new KeywordCountResponse(info.content(), info.count(), info.isPositive());
        }
    }

    public record TopReviewImageResponse(
            String url, Long reviewId, String blockCode, Integer rowNumber, Integer seatNumber) {

        public static TopReviewImageResponse from(TopReviewImage image) {
            return new TopReviewImageResponse(
                    image.getUrl(),
                    image.getReviewId(),
                    image.getBlockCode(),
                    image.getRowNumber(),
                    image.getSeatNumber());
        }
    }

    public record FilterInfo(Integer rowNumber, Integer seatNumber, Integer year, Integer month) {}
}
