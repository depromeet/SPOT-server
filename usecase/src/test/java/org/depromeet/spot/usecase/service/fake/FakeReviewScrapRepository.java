package org.depromeet.spot.usecase.service.fake;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;

public class FakeReviewScrapRepository implements ReviewScrapRepository {

    private final List<ReviewScrap> scraps = new ArrayList<>();
    private final List<Review> reviews = new ArrayList<>();
    private final List<Keyword> keywords = new ArrayList<>();

    @Override
    public List<Review> findScrappedReviewsByMemberId(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad,
            String cursor,
            SortCriteria sortBy,
            Integer size) {
        return reviews.stream()
                .filter(review -> isScraped(memberId, review.getId()))
                .filter(
                        review ->
                                stadiumId == null || review.getStadium().getId().equals(stadiumId))
                .filter(
                        review ->
                                months == null
                                        || months.isEmpty()
                                        || months.contains(review.getDateTime().getMonthValue()))
                .filter(review -> filterByKeywords(review, good, bad))
                .sorted((r1, r2) -> sortReviews(r1, r2, sortBy))
                .skip(getCursorIndex(cursor, sortBy))
                .limit(size + 1) // Get one extra to check if there are more results
                .collect(Collectors.toList());
    }

    @Override
    public Long getTotalCount(
            Long memberId,
            Long stadiumId,
            List<Integer> months,
            List<String> good,
            List<String> bad) {
        return reviews.stream()
                .filter(review -> isScraped(memberId, review.getId()))
                .filter(
                        review ->
                                stadiumId == null || review.getStadium().getId().equals(stadiumId))
                .filter(
                        review ->
                                months == null
                                        || months.isEmpty()
                                        || months.contains(review.getDateTime().getMonthValue()))
                .filter(review -> filterByKeywords(review, good, bad))
                .count();
    }

    @Override
    public boolean existsBy(final long memberId, final long reviewId) {
        return scraps.stream()
                .anyMatch(
                        scrap ->
                                scrap.getMemberId() == memberId && scrap.getReviewId() == reviewId);
    }

    @Override
    public long countByReview(final long reviewId) {
        return scraps.stream().filter(scrap -> scrap.getReviewId() == reviewId).count();
    }

    @Override
    public void deleteBy(final long memberId, final long reviewId) {
        scraps.removeIf(
                scrap -> scrap.getMemberId() == memberId && scrap.getReviewId() == reviewId);
    }

    @Override
    public void save(ReviewScrap scrap) {
        deleteBy(scrap.getMemberId(), scrap.getReviewId());
        scraps.add(scrap);
    }

    @Override
    public Map<Long, Boolean> existsByMemberIdAndReviewIds(Long memberId, List<Long> reviewIds) {
        return Map.of();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void addKeyword(Keyword keyword) {
        keywords.add(keyword);
    }

    private boolean isScraped(Long memberId, Long reviewId) {
        return scraps.stream()
                .anyMatch(
                        scrap ->
                                scrap.getMemberId().equals(memberId)
                                        && scrap.getReviewId().equals(reviewId));
    }

    private boolean filterByKeywords(Review review, List<String> good, List<String> bad) {
        boolean hasGood =
                good == null
                        || good.isEmpty()
                        || review.getKeywords().stream()
                                .anyMatch(
                                        reviewKeyword -> {
                                            Keyword keyword =
                                                    getKeywordById(reviewKeyword.getKeywordId());
                                            return keyword != null
                                                    && good.contains(keyword.getContent())
                                                    && keyword.getIsPositive();
                                        });
        boolean hasBad =
                bad == null
                        || bad.isEmpty()
                        || review.getKeywords().stream()
                                .anyMatch(
                                        reviewKeyword -> {
                                            Keyword keyword =
                                                    getKeywordById(reviewKeyword.getKeywordId());
                                            return keyword != null
                                                    && bad.contains(keyword.getContent())
                                                    && !keyword.getIsPositive();
                                        });
        return hasGood && hasBad;
    }

    private Keyword getKeywordById(Long keywordId) {
        return keywords.stream().filter(k -> k.getId().equals(keywordId)).findFirst().orElse(null);
    }

    private int sortReviews(Review r1, Review r2, SortCriteria sortBy) {
        switch (sortBy) {
            case LIKES_COUNT:
                int likesCompare = r2.getLikesCount();
                if (likesCompare != 0) return likesCompare;
                // If likes count is the same, sort by date
                return r2.getDateTime().compareTo(r1.getDateTime());
            case DATE_TIME:
            default:
                return r2.getDateTime().compareTo(r1.getDateTime());
        }
    }

    private long getCursorIndex(String cursor, SortCriteria sortBy) {
        if (cursor == null) return 0;
        String[] parts = cursor.split("_");
        if (parts.length != 3) return 0;

        LocalDateTime dateTime = LocalDateTime.parse(parts[0]);
        int likesCount = Integer.parseInt(parts[1]);
        long id = Long.parseLong(parts[2]);

        return reviews.stream()
                .filter(
                        review -> {
                            switch (sortBy) {
                                case LIKES_COUNT:
                                    return review.getLikesCount() > likesCount
                                            || (review.getLikesCount() == likesCount
                                                    && review.getDateTime().isAfter(dateTime))
                                            || (review.getLikesCount() == likesCount
                                                    && review.getDateTime().equals(dateTime)
                                                    && review.getId() > id);
                                case DATE_TIME:
                                default:
                                    return review.getDateTime().isAfter(dateTime)
                                            || (review.getDateTime().equals(dateTime)
                                                    && review.getId() > id);
                            }
                        })
                .count();
    }

    public void clear() {
        scraps.clear();
        reviews.clear();
        keywords.clear();
    }
}
