package org.depromeet.spot.usecase.service.review.processor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.usecase.port.out.review.ReviewLikeRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReadReviewProcessorImpl implements ReadReviewProcessor {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewScrapRepository reviewScrapRepository;

    public void setLikedAndScrappedStatus(List<Review> reviews, Long memberId) {
        List<Long> reviewIds = reviews.stream().map(Review::getId).collect(Collectors.toList());

        Map<Long, Boolean> likedMap =
                reviewLikeRepository.existsByMemberIdAndReviewIds(memberId, reviewIds);
        Map<Long, Boolean> scrappedMap =
                reviewScrapRepository.existsByMemberIdAndReviewIds(memberId, reviewIds);

        reviews.forEach(
                review -> {
                    boolean isLiked = likedMap.getOrDefault(review.getId(), false);
                    boolean isScrapped = scrappedMap.getOrDefault(review.getId(), false);
                    review.setLikedAndScrapped(isLiked, isScrapped);
                });
    }

    @Override
    public void setLikedAndScrappedStatus(Review review, Long memberId) {
        boolean isLiked = reviewLikeRepository.existsBy(memberId, review.getId());
        boolean isScrapped = reviewScrapRepository.existsBy(memberId, review.getId());
        review.setLikedAndScrapped(isLiked, isScrapped);
    }
}
