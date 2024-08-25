package org.depromeet.spot.usecase.service.review.scrap;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.page.PageCommand;
import org.depromeet.spot.usecase.port.in.review.scrap.ReviewScrapUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;
import org.depromeet.spot.usecase.service.review.ReadReviewService;
import org.depromeet.spot.usecase.service.review.processor.ReadReviewProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewScrapService implements ReviewScrapUsecase {

    private final ReadReviewUsecase readReviewUsecase;
    private final UpdateReviewUsecase updateReviewUsecase;
    private final ReviewScrapRepository scrapRepository;
    private final ReadReviewService readReviewService;
    private final ReadReviewProcessor readReviewProcessor;

    @Override
    public MyScrapListResult findMyScrappedReviews(
            Long memberId, MyScrapCommand command, PageCommand pageCommand) {

        List<Review> reviews =
                scrapRepository.findScrappedReviewsByMemberId(
                        memberId,
                        command.stadiumId(),
                        command.months(),
                        command.good(),
                        command.bad(),
                        pageCommand.cursor(),
                        pageCommand.sortBy(),
                        pageCommand.size() + 1);

        boolean hasNext = reviews.size() > pageCommand.size();
        if (hasNext) {
            reviews = reviews.subList(0, pageCommand.size());
        }

        String nextCursor =
                hasNext
                        ? readReviewService.getCursor(
                                reviews.get(reviews.size() - 1), pageCommand.sortBy())
                        : null;

        List<Review> reviewsWithKeywords = readReviewService.mapKeywordsToReviews(reviews);

        // 유저의 리뷰 좋아요, 스크랩 여부
        readReviewProcessor.setLikedAndScrappedStatus(reviewsWithKeywords, memberId);

        Long totalScrapCount =
                scrapRepository.getTotalCount(
                        memberId,
                        command.stadiumId(),
                        command.months(),
                        command.good(),
                        command.bad());

        return MyScrapListResult.builder()
                .reviews(reviewsWithKeywords)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .totalScrapCount(totalScrapCount)
                .build();
    }

    @Override
    public boolean toggleScrap(final long memberId, final long reviewId) {
        Review review = readReviewUsecase.findById(reviewId);

        if (isScraped(memberId, reviewId)) {
            cancelScrap(memberId, reviewId, review);
            return false;
        }

        addScrap(memberId, reviewId, review);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean isScraped(final long memberId, final long reviewId) {
        return scrapRepository.existsBy(memberId, reviewId);
    }

    public void cancelScrap(final long memberId, final long reviewId, Review review) {
        scrapRepository.deleteBy(memberId, reviewId);
        review.cancelScrap();
        updateReviewUsecase.updateScrapsCount(review);
    }

    public void addScrap(final long memberId, final long reviewId, Review review) {
        ReviewScrap like = ReviewScrap.builder().memberId(memberId).reviewId(reviewId).build();
        scrapRepository.save(like);
        review.addScrap();
        updateReviewUsecase.updateScrapsCount(review);
    }
}
