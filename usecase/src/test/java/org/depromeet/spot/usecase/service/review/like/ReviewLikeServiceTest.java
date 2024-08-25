package org.depromeet.spot.usecase.service.review.like;

import org.depromeet.spot.usecase.service.fake.FakeBaseballTeamRepository;
import org.depromeet.spot.usecase.service.fake.FakeBlockTopKeywordRepository;
import org.depromeet.spot.usecase.service.fake.FakeKeywordRepository;
import org.depromeet.spot.usecase.service.fake.FakeMemberRepository;
import org.depromeet.spot.usecase.service.fake.FakeReviewImageRepository;
import org.depromeet.spot.usecase.service.fake.FakeReviewLikeRepository;
import org.depromeet.spot.usecase.service.fake.FakeReviewRepositoryV2;
import org.depromeet.spot.usecase.service.fake.FakeSeatRepository;
import org.depromeet.spot.usecase.service.review.ReadReviewService;
import org.depromeet.spot.usecase.service.review.UpdateReviewService;
import org.junit.jupiter.api.BeforeEach;

class ReviewLikeServiceTest {

    private ReviewLikeService reviewLikeService;

    @BeforeEach
    void init() {
        FakeReviewLikeRepository reviewLikeRepository = new FakeReviewLikeRepository();
        this.reviewLikeService =
                ReviewLikeService.builder()
                        .reviewLikeRepository(reviewLikeRepository)
                        .readReviewUsecase(getReadReviewService())
                        .updateReviewUsecase(getUpdateReviewService())
                        .build();
    }

    private ReadReviewService getReadReviewService() {
        return ReadReviewService.builder()
                .reviewRepository(new FakeReviewRepositoryV2())
                .reviewImageRepository(new FakeReviewImageRepository())
                .blockTopKeywordRepository(new FakeBlockTopKeywordRepository())
                .keywordRepository(new FakeKeywordRepository())
                .memberRepository(new FakeMemberRepository())
                .baseballTeamRepository(new FakeBaseballTeamRepository())
                .build();
    }

    private UpdateReviewService getUpdateReviewService() {
        return UpdateReviewService.builder()
                .reviewRepository(new FakeReviewRepositoryV2())
                .seatRepository(new FakeSeatRepository())
                .keywordRepository(new FakeKeywordRepository())
                .blockTopKeywordRepository(new FakeBlockTopKeywordRepository())
                .build();
    }
}
