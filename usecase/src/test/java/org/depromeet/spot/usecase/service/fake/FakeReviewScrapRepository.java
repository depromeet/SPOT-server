package org.depromeet.spot.usecase.service.fake;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.spot.domain.review.scrap.ReviewScrap;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;

public class FakeReviewScrapRepository implements ReviewScrapRepository {

    private final List<ReviewScrap> scraps = new ArrayList<>();

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
        // 이미 존재하는 경우 업데이트, 그렇지 않으면 새로 추가
        deleteBy(scrap.getMemberId(), scrap.getReviewId());
        scraps.add(scrap);
    }

    public void clear() {
        scraps.clear();
    }

    public List<ReviewScrap> findAll() {
        return new ArrayList<>(scraps);
    }
}
