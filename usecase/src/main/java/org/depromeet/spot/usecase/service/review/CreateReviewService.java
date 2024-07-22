package org.depromeet.spot.usecase.service.review;

// import org.depromeet.spot.common.exception.member.MemberException.MemberNotFoundException;
// import org.depromeet.spot.common.exception.seat.SeatException.SeatNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateReviewService implements CreateReviewUsecase {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public CreateReviewResult create(Long seatId, Long memberId, CreateReviewCommand command) {
        // 1. 멤버와 좌석 정보 조회
        Member member = memberRepository.findById(memberId);
        //            .orElseThrow(() -> new MemberNotFoundException(memberId));
        Seat seat = seatRepository.findById(seatId);
        //            .orElseThrow(() -> new SeatNotFoundException(seatId));

        // 1.5 Seat을 통해 Stadium, Section, Block 다 가지고오기
        Stadium stadium = seat.getStadium();
        Section section = seat.getSection();
        Block block = seat.getBlock();
        BlockRow row = seat.getRow();

        // 2. Review 저장
        Review savedReview =
                Review.builder()
                        .member(member)
                        .stadium(stadium)
                        .section(section)
                        .block(block)
                        .row(row)
                        .seat(seat)
                        .dateTime(command.dateTime())
                        .content(command.content())
                        .build();
        savedReview = reviewRepository.save(savedReview);

        // 3. ReviewImage 저장
        List<ReviewImage> reviewImages = new ArrayList<>();
        for (String imageUrl : command.images()) {
            ReviewImage reviewImage =
                    ReviewImage.builder().reviewId(savedReview.getId()).url(imageUrl).build();
            reviewImages.add(reviewRepository.saveReviewImage(reviewImage));
        }

        // 4. Keyword 및 ReviewKeyword 저장
        List<ReviewKeyword> reviewKeywords = new ArrayList<>();
        processKeywords(command.good(), true, savedReview.getId(), reviewKeywords);
        processKeywords(command.bad(), false, savedReview.getId(), reviewKeywords);

        // 5. BlockTopKeyword 업데이트
        updateBlockTopKeywords(seat.getBlock().getId(), reviewKeywords);

        Review updatedReview = savedReview.addImagesAndKeywords(reviewImages, reviewKeywords);

        // 결과 반환
        return new CreateReviewResult(updatedReview, member, seat);
    }

    private void processKeywords(
            List<String> keywordContents,
            boolean isPositive,
            Long reviewId,
            List<ReviewKeyword> reviewKeywords) {
        for (String content : keywordContents) {
            Keyword keyword =
                    reviewRepository
                            .findKeywordByContent(content)
                            .orElseGet(
                                    () ->
                                            reviewRepository.saveKeyword(
                                                    Keyword.builder()
                                                            .content(content)
                                                            .isPositive(isPositive)
                                                            .build()));

            ReviewKeyword reviewKeyword =
                    ReviewKeyword.builder().reviewId(reviewId).keyword(keyword).build();
            reviewKeywords.add(reviewRepository.saveReviewKeyword(reviewKeyword));
        }
    }

    private void updateBlockTopKeywords(Long blockId, List<ReviewKeyword> reviewKeywords) {
        for (ReviewKeyword reviewKeyword : reviewKeywords) {
            reviewRepository.updateBlockTopKeyword(blockId, reviewKeyword.getKeyword().getId());
        }
    }
}
