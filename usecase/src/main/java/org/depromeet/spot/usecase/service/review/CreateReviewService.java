package org.depromeet.spot.usecase.service.review;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.block.ReadBlockRowUsecase;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.seat.SeatRepository;
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

    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final UpdateMemberUsecase updateMemberUsecase;
    private final ReadReviewUsecase readReviewUsecase;
    private final StadiumReadUsecase stadiumReadUsecase;
    private final SectionReadUsecase sectionReadUsecase;
    private final ReadBlockRowUsecase readBlockRowUsecase;
    private final ReviewImageProcessor reviewImageProcessor;
    private final ReviewKeywordProcessor reviewKeywordProcessor;

    @Override
    @Transactional
    public CreateReviewResult create(
            Long blockId, Integer seatNumber, Long memberId, CreateReviewCommand command) {
        Member member = memberRepository.findById(memberId);
        Seat seat = seatRepository.findByIdWith(blockId, seatNumber);

        // image와 keyword를 제외한 review 도메인 생성
        Review review = convertToDomain(seat, member, command);

        // review 도메인에 keyword와 image를 추가
        Map<Long, Keyword> keywordMap =
                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
        reviewImageProcessor.processImages(review, command.images());

        // 저장 및 blockTopKeyword에도 count 업데이트
        Review savedReview = reviewRepository.save(review);

        // BlockTopKeyword 업데이트 및 생성
        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);

        savedReview.setKeywordMap(keywordMap);

        // 회원 리뷰 경험치 업데이트
        Member levelUpdateMember = calculateMemberLevel(member);

        return new CreateReviewResult(savedReview, levelUpdateMember, seat);
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
        BlockRow blockRow = readBlockRowUsecase.findBy(stadiumId, blockCode, rowNumber);
        Block block = blockRow.getBlock();
        Seat seat = getSeat(block.getId(), command.seatNumber());

        Review review = convertToDomain(member, blockRow, seat, command);
        Review savedReview = reviewRepository.save(review);

        List<String> imageUrls = reviewImageProcessor.getImageUrl(command.images());
        reviewImageProcessor.processImages(review, imageUrls);

        Map<Long, Keyword> keywordMap =
                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);
        savedReview.setKeywordMap(keywordMap);

        calculateMemberLevel(member);
    }

    private Seat getSeat(long blockId, Integer seatNumber) {
        if (seatNumber == null) return null;
        else return seatRepository.findByIdWith(blockId, seatNumber);
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

    private Review convertToDomain(
            Member member, BlockRow blockRow, Seat seat, CreateAdminReviewCommand command) {
        Block block = blockRow.getBlock();
        Stadium stadium = stadiumReadUsecase.findById(block.getStadiumId());
        Section section = sectionReadUsecase.findById(block.getSectionId());

        return Review.builder()
                .member(member)
                .stadium(stadium)
                .section(section)
                .block(block)
                .row(blockRow)
                .seat(seat)
                .dateTime(command.dateTime())
                .content(command.content())
                .build();
    }

    public Member calculateMemberLevel(final Member member) {
        final long memberReviewCnt = readReviewUsecase.countByMember(member.getId());
        return updateMemberUsecase.updateLevel(member, memberReviewCnt);
    }
}
