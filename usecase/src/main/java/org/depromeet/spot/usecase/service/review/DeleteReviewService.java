package org.depromeet.spot.usecase.service.review;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.usecase.port.in.member.ReadMemberUsecase;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.depromeet.spot.usecase.port.in.review.DeleteReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteReviewService implements DeleteReviewUsecase {

    private final ReviewRepository reviewRepository;
    private final ReadReviewUsecase readReviewUsecase;
    private final UpdateMemberUsecase updateMemberUsecase;
    private final ReadMemberUsecase readMemberUsecase;

    @Override
    @Transactional
    public Long deleteReview(Long reviewId, Long memberId) {
        Long deletedReviewId = reviewRepository.softDeleteByIdAndMemberId(reviewId, memberId);
        updateMemberLevel(memberId);
        return deletedReviewId;
    }

    @Override
    public void deleteAllReviewOwnedByMemberId(Long memberId) {
        reviewRepository.softDeleteAllReviewOwnedByMemberId(memberId);
    }

    public void updateMemberLevel(Long memberId) {
        Member member = readMemberUsecase.findById(memberId);
        long reviewCnt = readReviewUsecase.countByMember(memberId);
        updateMemberUsecase.updateLevel(member, reviewCnt);
    }
}
