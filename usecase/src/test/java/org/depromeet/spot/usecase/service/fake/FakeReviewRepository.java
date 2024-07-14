package org.depromeet.spot.usecase.service.fake;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.depromeet.spot.common.exception.review.ReviewException;
import org.depromeet.spot.domain.review.KeywordCount;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewKeyword;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;

public class FakeReviewRepository implements ReviewRepository {

    private final List<Review> data = new ArrayList<>();

    @Override
    public List<Review> findByBlockId(
            Long stadiumId, Long blockId, Long rowId, Long seatNumber, int offset, int limit) {
        List<Review> filteredReviews =
                data.stream()
                        .filter(
                                review ->
                                        review.getStadiumId().equals(stadiumId)
                                                && review.getBlockId().equals(blockId))
                        .filter(review -> rowId == null || review.getRowId().equals(rowId))
                        .filter(
                                review ->
                                        seatNumber == null
                                                || review.getSeatNumber().equals(seatNumber))
                        .skip(offset)
                        .limit(limit)
                        .collect(Collectors.toList());

        if (filteredReviews.isEmpty()) {
            throw new ReviewException.ReviewNotFoundException(
                    "No review found for blockId:" + blockId);
        }

        return filteredReviews;
    }

    @Override
    public int countByBlockId(Long stadiumId, Long blockId, Long rowId, Long seatNumber) {
        return (int)
                data.stream()
                        .filter(
                                review ->
                                        review.getStadiumId().equals(stadiumId)
                                                && review.getBlockId().equals(blockId))
                        .filter(review -> rowId == null || review.getRowId().equals(rowId))
                        .filter(
                                review ->
                                        seatNumber == null
                                                || review.getSeatNumber().equals(seatNumber))
                        .count();
    }

    @Override
    public List<KeywordCount> findTopKeywordsByBlockId(Long stadiumId, Long blockId, int limit) {
        Map<Long, Long> keywordCounts =
                data.stream()
                        .filter(
                                review ->
                                        review.getStadiumId().equals(stadiumId)
                                                && review.getBlockId().equals(blockId))
                        .flatMap(
                                review ->
                                        review.getKeywords() != null
                                                ? review.getKeywords().stream()
                                                : Stream.empty())
                        .collect(
                                Collectors.groupingBy(
                                        ReviewKeyword::getKeywordId, Collectors.counting()));

        return keywordCounts.entrySet().stream()
                .map(entry -> new KeywordCount(entry.getKey().toString(), entry.getValue()))
                .sorted(Comparator.comparing(KeywordCount::count).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void save(Review review) {
        data.add(review);
    }

    public void clear() {
        data.clear();
    }
}
