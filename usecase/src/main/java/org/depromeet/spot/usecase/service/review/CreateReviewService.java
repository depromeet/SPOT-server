package org.depromeet.spot.usecase.service.review;

// import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
// import org.depromeet.spot.common.exception.seat.SeatException.SeatNotFoundException;
import java.util.List;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateReviewService implements CreateReviewUsecase {

    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final KeywordRepository keywordRepository;
    private final BlockTopKeywordRepository blockTopKeywordRepository;
    private final UpdateMemberUsecase updateMemberUsecase;

    @Override
    @Transactional
    public CreateReviewResult create(Long seatId, Long memberId, CreateReviewCommand command) {
        // ToDo: orElseThrow not found exception 처리하기
        Member member = memberRepository.findById(memberId);
        Seat seat = seatRepository.findByIdWith(seatId);

        // image와 keyword를 제외한 review 도메인 생성
        Review review = convertToDomain(seat, member, command);

        // review 도메인에 keyword와 image를 추가
        processKeywords(review, command.good(), command.bad());
        processImages(review, command.images());

        // 저장 및 blockTopKeyword에도 count 업데이트
        Review savedReview = reviewRepository.save(review);
        updateBlockTopKeywords(savedReview);

        // 회원 리뷰 경험치 업데이트
        calculateMemberLevel(member);

        return new CreateReviewResult(savedReview, member, seat);
    }

    private Review convertToDomain(Seat seat, Member member, CreateReviewCommand command) {
        return Review.builder()
                .member(member)
                .stadium(seat.getStadium())
                .section(seat.getSection())
                .block(seat.getBlock())
                .row(seat.getRow())
                .seat(seat)
                .dateTime(command.dateTime())
                .content(command.content())
                .build();
    }

    public void calculateMemberLevel(final Member member) {
        final long memberReviewCnt = reviewRepository.countByUserId(member.getId());
        updateMemberUsecase.updateLevel(member, memberReviewCnt);
    }

    private void processKeywords(
            Review review, List<String> goodKeywords, List<String> badKeywords) {
        processKeywordList(review, goodKeywords, true);
        processKeywordList(review, badKeywords, false);
    }

    private void processKeywordList(
            Review review, List<String> keywordContents, boolean isPositive) {
        for (String content : keywordContents) {
            Keyword keyword =
                    keywordRepository
                            .findByContent(content)
                            .orElseGet(
                                    () ->
                                            keywordRepository.save(
                                                    Keyword.create(null, content, isPositive)));
            ReviewKeyword reviewKeyword = ReviewKeyword.create(null, keyword.getId());
            review.addKeyword(reviewKeyword);
        }
    }

    private void processImages(Review review, List<String> imageUrls) {
        for (String url : imageUrls) {
            ReviewImage image = ReviewImage.create(null, review, url);
            review.addImage(image);
        }
    }

    private void updateBlockTopKeywords(Review review) {
        for (ReviewKeyword reviewKeyword : review.getKeywords()) {
            blockTopKeywordRepository.updateKeywordCount(
                    review.getBlock().getId(), reviewKeyword.getKeywordId());
        }
    }
}
