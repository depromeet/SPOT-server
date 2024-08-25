package org.depromeet.spot.usecase.service.fake;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.LocationInfo;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;

// FIXME: 구현 채워두기
public class FakeReviewRepositoryV2 implements ReviewRepository {

    @Override
    public void updateLikesCount(Long reviewId, int likesCount) {}

    @Override
    public void updateScrapsCount(Long reviewId, int likesCount) {}

    @Override
    public Review save(Review review) {
        return null;
    }

    @Override
    public Review findById(Long id) {
        return null;
    }

    @Override
    public long countByUserId(Long userId) {
        return 0;
    }

    @Override
    public List<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size) {
        return null;
    }

    @Override
    public List<Review> findAllByUserId(
            Long userId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size) {
        return null;
    }

    @Override
    public List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId) {
        return null;
    }

    @Override
    public Long softDeleteByIdAndMemberId(Long reviewId, Long memberId) {
        return null;
    }

    @Override
    public LocationInfo findLocationInfoByStadiumIdAndBlockCode(Long stadiumId, String blockCode) {
        return null;
    }

    @Override
    public Review findLastReviewByMemberId(Long memberId) {
        return null;
    }

    @Override
    public long countByIdByMemberId(Long memberId) {
        return 0;
    }

    @Override
    public long countByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month) {
        return 0;
    }
}
