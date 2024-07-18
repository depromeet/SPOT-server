package org.depromeet.spot.usecase.service.review;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewImage;
import org.depromeet.spot.domain.review.ReviewKeyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.member.ReadMemberUsecase;
import org.depromeet.spot.usecase.port.in.member.UpdateMemberUsecase;
import org.depromeet.spot.usecase.port.in.review.CreateReviewUsecase;
import org.depromeet.spot.usecase.port.in.seat.ReadSeatUsecase;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateReviewService implements CreateReviewUsecase {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReadSeatUsecase readSeatUsecase;
    private final ReadMemberUsecase readMemberUsecase;
    private final UpdateMemberUsecase updateMemberUsecase;

    @Override
    public Review create(final Long seatId, final Long memberId, CreateReviewCommand command) {
        Seat seat = readSeatUsecase.findById(seatId);
        Member member = readMemberUsecase.findById(memberId);

        Review review = reviewRepository.save(convertToDomain(seat, member, command));

        List<String> imageUrls = command.images();
        List<ReviewImage> images =
                reviewImageRepository.saveAll(
                        imageUrls.stream()
                                .map(url -> ReviewImage.of(review.getId(), url))
                                .toList());

        // TODO: 리뷰 키워드 저장
        List<ReviewKeyword> keywords = new ArrayList<>();

        // TODO: 리뷰 수를 이용해 레벨 조정

        return review.addImagesAndKeywords(images, keywords);
    }

    private Review convertToDomain(Seat seat, Member member, CreateReviewCommand command) {
        return Review.builder()
                .userId(member.getId())
                .stadiumId(seat.getStadium().getId())
                .blockId(seat.getBlock().getId())
                .seatId(seat.getId())
                .rowId(seat.getRow().getId())
                .seatNumber(Long.valueOf(seat.getSeatNumber()))
                .dateTime(command.dateTime())
                .content(command.content())
                .build();
    }
}
