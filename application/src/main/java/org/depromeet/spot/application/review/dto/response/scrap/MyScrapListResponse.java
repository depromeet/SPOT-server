package org.depromeet.spot.application.review.dto.response.scrap;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.application.review.dto.response.BaseReviewResponse;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase.MyScrapCommand;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase.MyScrapListResult;

public record MyScrapListResponse(
        List<MyScrapResponse> reviews,
        String nextCursor,
        boolean hasNext,
        Long totalScrapCount,
        ScrapFilter filter) {

    public static MyScrapListResponse from(MyScrapListResult result, MyScrapCommand command) {

        List<MyScrapResponse> reviews =
                result.reviews().stream().map(MyScrapResponse::from).collect(Collectors.toList());
        ScrapFilter filter =
                new ScrapFilter(
                        command.stadiumId(), command.months(), command.good(), command.bad());

        return new MyScrapListResponse(
                reviews, result.nextCursor(), result.hasNext(), result.totalScrapCount(), filter);
    }

    public record MyScrapResponse(
            BaseReviewResponse baseReview,
            String stadiumName,
            String sectionName,
            String blockCode) {

        public static MyScrapResponse from(Review review) {
            return new MyScrapResponse(
                    BaseReviewResponse.from(review),
                    review.getStadium().getName(),
                    review.getSection().getName(),
                    review.getBlock().getCode());
        }
    }

    public record ScrapFilter(
            Long stadiumId, List<Integer> months, List<String> good, List<String> bad) {}
}
