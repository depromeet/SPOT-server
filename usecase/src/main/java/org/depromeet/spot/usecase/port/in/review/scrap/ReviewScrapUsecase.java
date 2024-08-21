package org.depromeet.spot.usecase.port.in.review.scrap;

public interface ReviewScrapUsecase {
    boolean toggleScrap(long memberId, long reviewId);
}
