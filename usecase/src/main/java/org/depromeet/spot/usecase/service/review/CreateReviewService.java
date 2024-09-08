package org.depromeet.spot.usecase.service.review;

import java.util.Map;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.mixpanel.MixpanelEvent;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.mixpanel.MixpanelRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.service.member.processor.MemberLevelProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewCreationProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewImageProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReviewKeywordProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateReviewService implements CreateReviewUsecase {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCreationProcessor reviewCreationProcessor;
    private final ReviewImageProcessor reviewImageProcessor;
    private final ReviewKeywordProcessor reviewKeywordProcessor;
    private final MemberLevelProcessor memberLevelProcessor;
    private final MixpanelRepository mixpanelRepository;

    @Override
    @Transactional
    public CreateReviewResult create(Long blockId, Long memberId, CreateReviewCommand command) {
        Member member = memberRepository.findById(memberId);

        Review review = reviewCreationProcessor.createReview(blockId, member, command);

        Map<Long, Keyword> keywordMap =
                reviewCreationProcessor.processReviewDetails(review, command);

        Review savedReview = reviewRepository.save(review);
        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);
        savedReview.setKeywordMap(keywordMap);

        Member levelUpdateMember = memberLevelProcessor.calculateAndUpdateMemberLevel(member);

        // 믹스패널 이벤트(후기 등록 완료) 호출
        mixpanelRepository.eventTrack(MixpanelEvent.REVIEW_REGISTER, String.valueOf(memberId));

        return new CreateReviewResult(savedReview, levelUpdateMember, review.getSeat());
    }

    @Override
    @Transactional
    public void createAdmin(
            long stadiumId,
            String blockCode,
            int rowNumber,
            Long memberId,
            CreateAdminReviewCommand command) {
        Member member = memberRepository.findById(memberId);

        Review review =
                reviewCreationProcessor.createAdminReview(
                        stadiumId, blockCode, rowNumber, member, command);

        Map<Long, Keyword> keywordMap =
                reviewCreationProcessor.processAdminReviewDetails(review, command);

        Review savedReview = reviewRepository.save(review);
        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);
        savedReview.setKeywordMap(keywordMap);

        memberLevelProcessor.calculateAndUpdateMemberLevel(member);
    }
}
