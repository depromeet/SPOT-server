// package org.depromeet.spot.application.review.dto.response;
//
// import java.util.List;
// import java.util.stream.Collectors;
//
// import org.depromeet.spot.domain.review.result.BlockReviewListResult;
//
// public record BlockReviewListResponse(
//        List<KeywordCountResponse> keywords,
//        List<BaseReviewResponse> reviews,
//        List<TopReviewImage> topReviewImages,
//        long totalElements,
//        int totalPages,
//        int number,
//        int size,
//        boolean first,
//        boolean last,
//        FilterInfo filter) {
//
//    public static BlockReviewListResponse from(
//            BlockReviewListResult result, Integer rowNumber, Integer seatNumber) {
//        List<BaseReviewResponse> reviewResponses =
//                result.getReviews().stream()
//                        .map(BaseReviewResponse::from)
//                        .collect(Collectors.toList());
//
//        List<KeywordCountResponse> keywordResponses =
//                result.getTopKeywords().stream()
//                        .map(
//                                kc ->
//                                        new KeywordCountResponse(
//                                                kc.getContent(), kc.getCount(),
// kc.getIsPositive()))
//                        .collect(Collectors.toList());
//
//        List<TopReviewImage> topReviewImages =
//                result.getTopReviewImages().stream()
//                        .map(
//                                image ->
//                                        new TopReviewImage(
//                                                image.getUrl(),
//                                                image.getReviewId().getBlock().getCode(),
//                                                image.getReview().getRow().getNumber(),
//                                                image.getReview().getSeat().getSeatNumber()))
//                        .limit(5)
//                        .collect(Collectors.toList());
//
//        FilterInfo filter = new FilterInfo(rowNumber, seatNumber);
//
//        boolean first = result.getNumber() == 0;
//        boolean last = result.getNumber() == result.getTotalPages() - 1;
//
//        return new BlockReviewListResponse(
//                keywordResponses,
//                reviewResponses,
//                topReviewImages,
//                result.getTotalElements(),
//                result.getTotalPages(),
//                result.getNumber(),
//                result.getSize(),
//                first,
//                last,
//                filter);
//    }
//
//    public record KeywordCountResponse(String content, Long count, Boolean isPositive) {}
//
//    public record TopReviewImage(
//            String url, String blockCode, Integer rowNumber, Integer seatNumber) {}
//
//    public record FilterInfo(Integer rowNumber, Integer seatNumber) {}
// }
