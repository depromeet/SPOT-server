package org.depromeet.spot.application.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.image.TopReviewImage;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockKeywordInfo;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockReviewListResult;

public record BlockReviewListResponse(
        List<KeywordCountResponse> keywords,
        List<BaseReviewResponse> reviews,
        List<TopReviewImageResponse> topReviewImages,
        long totalElements,
        int totalPages,
        int number,
        int size,
        boolean first,
        boolean last,
        FilterInfo filter) {

    public static BlockReviewListResponse from(
            BlockReviewListResult result,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month) {
        List<BaseReviewResponse> reviewResponses =
                result.reviews().stream()
                        .map(BaseReviewResponse::from)
                        .collect(Collectors.toList());

        List<KeywordCountResponse> keywordResponses =
                result.topKeywords().stream()
                        .map(KeywordCountResponse::from)
                        .collect(Collectors.toList());

        List<TopReviewImageResponse> topReviewImageResponses =
                result.topReviewImages().stream()
                        .map(TopReviewImageResponse::from)
                        .collect(Collectors.toList());

        FilterInfo filter = new FilterInfo(rowNumber, seatNumber, year, month);

        boolean first = result.number() == 0;
        boolean last = result.number() == result.totalPages() - 1;

        return new BlockReviewListResponse(
                keywordResponses,
                reviewResponses,
                topReviewImageResponses,
                result.totalElements(),
                result.totalPages(),
                result.number(),
                result.size(),
                first,
                last,
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
