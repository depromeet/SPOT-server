package org.depromeet.spot.usecase.service.review;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
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

    @Override
    @Transactional
    public CreateReviewResult create(Long blockId, Long memberId, CreateReviewCommand command) {
        Member member = memberRepository.findById(memberId);

        Review review = reviewCreationProcessor.createReview(blockId, member, command);

        processReviewDetails(review, command);

        Review savedReview = reviewRepository.save(review);
        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);

        Member levelUpdateMember = memberLevelProcessor.calculateAndUpdateMemberLevel(member);

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

        processAdminReviewDetails(review, command);

        Review savedReview = reviewRepository.save(review);
        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);

        memberLevelProcessor.calculateAndUpdateMemberLevel(member);
    }

    private void processReviewDetails(Review review, CreateReviewCommand command) {
        Map<Long, Keyword> keywordMap =
                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
        review.setKeywordMap(keywordMap);
        reviewImageProcessor.processImages(review, command.images());
    }

    private void processAdminReviewDetails(Review review, CreateAdminReviewCommand command) {
        Map<Long, Keyword> keywordMap =
                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
        review.setKeywordMap(keywordMap);
        List<String> imageUrls = reviewImageProcessor.getImageUrl(command.images());
        reviewImageProcessor.processImages(review, imageUrls);
    }

    //    @Override
    //    @Transactional
    //    public CreateReviewResult create(
    //            Long blockId, Integer seatNumber, Long memberId, CreateReviewCommand command) {
    //        Member member = memberRepository.findById(memberId);
    //        Seat seat = seatRepository.findByIdWith(blockId, seatNumber);
    //
    //        // image와 keyword를 제외한 review 도메인 생성
    //        Review review = convertToDomain(seat, member, command);
    //
    //        // review 도메인에 keyword와 image를 추가
    //        Map<Long, Keyword> keywordMap =
    //                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
    //        reviewImageProcessor.processImages(review, command.images());
    //
    //        // 저장 및 blockTopKeyword에도 count 업데이트
    //        Review savedReview = reviewRepository.save(review);
    //
    //        // BlockTopKeyword 업데이트 및 생성
    //        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);
    //
    //        savedReview.setKeywordMap(keywordMap);
    //
    //        // 회원 리뷰 경험치 업데이트
    //        Member levelUpdateMember = calculateMemberLevel(member);
    //
    //        return new CreateReviewResult(savedReview, levelUpdateMember, seat);
    //    }

    //    @Override
    //    @Transactional
    //    public CreateReviewResult create(Long blockId, Long memberId, CreateReviewCommand command)
    // {
    //        Member member = memberRepository.findById(memberId);
    //        Stadium stadium;
    //        Section section;
    //        Block block;
    //        BlockRow blockRow = null;
    //        Seat seat = null;
    //
    //        if (command.seatNumber() != null) {
    //            // seatNumber로 조회
    //            seat = getSeatWith(blockId, command.seatNumber());
    //            stadium = seat.getStadium();
    //            section = seat.getSection();
    //            block = seat.getBlock();
    //            blockRow = seat.getRow();
    //        } else if (command.rowNumber() != null) {
    //            // rowNumber로만 조회
    //            block = blockReadUsecase.findById(blockId);
    //            stadium = stadiumReadUsecase.findById(block.getStadiumId());
    //            section = sectionReadUsecase.findById(block.getSectionId());
    //            blockRow =
    //                    readBlockRowUsecase.findBy(
    //                            stadium.getId(), block.getCode(), command.rowNumber());
    //        } else {
    //            // blockId로만 조회
    //            block = blockReadUsecase.findById(blockId);
    //            stadium = stadiumReadUsecase.findById(block.getStadiumId());
    //            section = sectionReadUsecase.findById(block.getSectionId());
    //        }
    //
    //        // Review 도메인 생성
    //        Review review =
    //                Review.builder()
    //                        .member(member)
    //                        .stadium(stadium)
    //                        .section(section)
    //                        .block(block)
    //                        .row(blockRow)
    //                        .seat(seat)
    //                        .dateTime(command.dateTime())
    //                        .content(command.content())
    //                        .build();
    //
    //        // review 도메인에 keyword와 image를 추가
    //        Map<Long, Keyword> keywordMap =
    //                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
    //        reviewImageProcessor.processImages(review, command.images());
    //
    //        // 저장 및 blockTopKeyword에도 count 업데이트
    //        Review savedReview = reviewRepository.save(review);
    //
    //        // BlockTopKeyword 업데이트 및 생성
    //        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);
    //
    //        savedReview.setKeywordMap(keywordMap);
    //
    //        // 회원 리뷰 경험치 업데이트
    //        Member levelUpdateMember = calculateMemberLevel(member);
    //
    //        return new CreateReviewResult(savedReview, levelUpdateMember, seat);
    //    }
    //
    //    @Override
    //    @Transactional
    //    public void createAdmin(
    //            long stadiumId,
    //            String blockCode,
    //            int rowNumber,
    //            Long memberId,
    //            CreateAdminReviewCommand command) {
    //        Member member = memberRepository.findById(memberId);
    //        BlockRow blockRow = readBlockRowUsecase.findBy(stadiumId, blockCode, rowNumber);
    //        Block block = blockRow.getBlock();
    //        Seat seat = getSeatWith(block.getId(), command.seatNumber());
    //
    //        Review review = convertToDomain(member, blockRow, seat, command);
    //        List<String> imageUrls = reviewImageProcessor.getImageUrl(command.images());
    //        Map<Long, Keyword> keywordMap =
    //                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
    //        reviewImageProcessor.processImages(review, imageUrls);
    //
    //        Review savedReview = reviewRepository.save(review);
    //        reviewKeywordProcessor.updateBlockTopKeywords(savedReview);
    //        savedReview.setKeywordMap(keywordMap);
    //
    //        calculateMemberLevel(member);
    //    }
    //
    //    private Seat getSeatWith(long blockId, Integer seatNumber) {
    //        if (seatNumber == null) return null;
    //        else return seatRepository.findByIdWith(blockId, seatNumber);
    //    }
    //
    //    private Review convertToDomain(Seat seat, Member member, CreateReviewCommand command) {
    //        return Review.builder()
    //                .member(member)
    //                .stadium(seat.getStadium())
    //                .section(seat.getSection())
    //                .block(seat.getBlock())
    //                .row(seat.getRow())
    //                .seat(seat)
    //                .dateTime(command.dateTime())
    //                .content(command.content())
    //                .build();
    //    }
    //
    //    private Review convertToDomain(
    //            Block block, BlockRow blockRow, Seat seat, Member member, CreateReviewCommand
    // command) {
    //        Stadium stadium = stadiumReadUsecase.findById(block.getStadiumId());
    //        Section section = sectionReadUsecase.findById(block.getSectionId());
    //
    //        return Review.builder()
    //                .member(member)
    //                .stadium(stadium)
    //                .section(section)
    //                .block(block)
    //                .row(blockRow)
    //                .seat(seat)
    //                .dateTime(command.dateTime())
    //                .content(command.content())
    //                .build();
    //    }
    //
    //    private Review convertToDomain(
    //            Member member, BlockRow blockRow, Seat seat, CreateAdminReviewCommand command) {
    //        Block block = blockRow.getBlock();
    //        Stadium stadium = stadiumReadUsecase.findById(block.getStadiumId());
    //        Section section = sectionReadUsecase.findById(block.getSectionId());
    //
    //        return Review.builder()
    //                .member(member)
    //                .stadium(stadium)
    //                .section(section)
    //                .block(block)
    //                .row(blockRow)
    //                .seat(seat)
    //                .dateTime(command.dateTime())
    //                .content(command.content())
    //                .build();
    //    }
    //
    //    public Member calculateMemberLevel(final Member member) {
    //        final long memberReviewCnt = readReviewUsecase.countByMember(member.getId());
    //        return updateMemberUsecase.updateLevel(member, memberReviewCnt);
    //    }
}
