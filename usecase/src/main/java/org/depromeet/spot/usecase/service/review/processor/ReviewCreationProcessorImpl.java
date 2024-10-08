package org.depromeet.spot.usecase.service.review.processor;

import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.block.BlockRow;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.ReviewLocationInfo;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateAdminReviewCommand;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase.CreateReviewCommand;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase.UpdateReviewCommand;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewCreationProcessorImpl implements ReviewCreationProcessor {
    private final ReviewDataProcessor reviewDataProcessor;
    private final ReviewKeywordProcessor reviewKeywordProcessor;
    private final ReviewImageProcessor reviewImageProcessor;

    @Override
    public Review createReview(Long blockId, Member member, CreateReviewCommand command) {
        Stadium stadium;
        Section section;
        Block block;
        BlockRow blockRow = null;
        Seat seat = null;

        if (command.seatNumber() != null) {
            seat = reviewDataProcessor.getSeatWithDetails(blockId, command.seatNumber());
            stadium = seat.getStadium();
            section = seat.getSection();
            block = seat.getBlock();
            blockRow = seat.getRow();
        } else if (command.rowNumber() != null) {
            block = reviewDataProcessor.getBlock(blockId);
            stadium = reviewDataProcessor.getStadium(block.getStadiumId());
            section = reviewDataProcessor.getSection(block.getSectionId());
            blockRow =
                    reviewDataProcessor.getBlockRow(
                            stadium.getId(), block.getCode(), command.rowNumber());
        } else {
            block = reviewDataProcessor.getBlock(blockId);
            stadium = reviewDataProcessor.getStadium(block.getStadiumId());
            section = reviewDataProcessor.getSection(block.getSectionId());
        }

        return Review.builder()
                .member(member)
                .stadium(stadium)
                .section(section)
                .block(block)
                .row(blockRow)
                .seat(seat)
                .dateTime(command.dateTime())
                .content(command.content())
                .reviewType(command.reviewType() != null ? command.reviewType() : ReviewType.VIEW)
                .build();
    }

    @Override
    public Review createAdminReview(
            long stadiumId,
            String blockCode,
            int rowNumber,
            Member member,
            CreateAdminReviewCommand command) {
        BlockRow blockRow = reviewDataProcessor.getBlockRow(stadiumId, blockCode, rowNumber);
        Block block = blockRow.getBlock();
        Stadium stadium = reviewDataProcessor.getStadium(block.getStadiumId());
        Section section = reviewDataProcessor.getSection(block.getSectionId());
        Seat seat =
                command.seatNumber() != null
                        ? reviewDataProcessor.getSeat(block.getId(), command.seatNumber())
                        : null;

        return Review.builder()
                .member(member)
                .stadium(stadium)
                .section(section)
                .block(block)
                .row(blockRow)
                .seat(seat)
                .dateTime(command.dateTime())
                .content(command.content())
                .reviewType(ReviewType.VIEW)
                .build();
    }

    @Override
    public Map<Long, Keyword> processReviewDetails(Review review, CreateReviewCommand command) {
        Map<Long, Keyword> keywordMap =
                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
        review.setKeywordMap(keywordMap);
        reviewImageProcessor.processImages(review, command.images());
        return keywordMap;
    }

    @Override
    public Map<Long, Keyword> processReviewDetails(Review review, UpdateReviewCommand command) {
        Map<Long, Keyword> keywordMap =
                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
        review.setKeywordMap(keywordMap);
        reviewImageProcessor.processImages(review, command.images());
        return keywordMap;
    }

    @Override
    public Map<Long, Keyword> processAdminReviewDetails(
            Review review, CreateAdminReviewCommand command) {
        Map<Long, Keyword> keywordMap =
                reviewKeywordProcessor.processKeywords(review, command.good(), command.bad());
        review.setKeywordMap(keywordMap);
        List<String> imageUrls = reviewImageProcessor.getImageUrl(command.images());
        reviewImageProcessor.processImages(review, imageUrls);
        return keywordMap;
    }

    @Override
    public Review updateReviewData(Review existingReview, UpdateReviewCommand command) {
        Stadium stadium =
                reviewDataProcessor.getStadiumIfChanged(existingReview, command.stadiumId());
        ReviewLocationInfo locationInfo =
                reviewDataProcessor.getUpdatedLocationInfo(existingReview, command);

        return Review.builder()
                .id(existingReview.getId())
                .member(existingReview.getMember())
                .stadium(stadium)
                .section(locationInfo.section())
                .block(locationInfo.block())
                .row(locationInfo.row())
                .seat(locationInfo.seat())
                .dateTime(command.dateTime())
                .content(command.content())
                .likesCount(existingReview.getLikesCount())
                .scrapsCount(existingReview.getScrapsCount())
                .reviewType(command.reviewType())
                .build();
    }
}
