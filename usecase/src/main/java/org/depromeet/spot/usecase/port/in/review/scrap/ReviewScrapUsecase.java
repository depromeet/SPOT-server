package org.depromeet.spot.usecase.port.in.review.scrap;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.in.review.page.PageCommand;

import lombok.Builder;

public interface ReviewScrapUsecase {

    boolean toggleScrap(long memberId, long reviewId);

    MyScrapListResult findMyScrappedReviews(
            Long memberId, MyScrapCommand command, PageCommand pageCommand);

    @Builder
    record MyScrapCommand(
            Long stadiumId, List<Integer> months, List<String> good, List<String> bad) {}

    @Builder
    record MyScrapListResult(
            List<Review> reviews, String nextCursor, boolean hasNext, Long totalScrapCount) {}
}
