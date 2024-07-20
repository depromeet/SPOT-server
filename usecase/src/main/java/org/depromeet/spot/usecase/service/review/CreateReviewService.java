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
import org.depromeet.spot.usecase.port.out.review.ReviewKeywordRepository;
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
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReadSeatUsecase readSeatUsecase;
    private final ReadMemberUsecase readMemberUsecase;
    private final UpdateMemberUsecase updateMemberUsecase;

    @Override
    public CreateReviewResult create(
            final Long seatId, final Long memberId, CreateReviewCommand command) {
        Seat seat = readSeatUsecase.findById(seatId);
        Member member = readMemberUsecase.findById(memberId);

        Review review = reviewRepository.save(convertToDomain(seat, member, command));

        List<ReviewImage> images = saveReviewImages(review, command.images());
        List<ReviewKeyword> keywords = saveReviewKeywords(review, command.good(), command.bad());

        review = review.addImagesAndKeywords(images, keywords);

        calculateMemberLevel(member);

        return new CreateReviewResult(review, member, seat);
    }

    private List<ReviewImage> saveReviewImages(Review review, List<String> imageUrls) {
        List<ReviewImage> images =
                imageUrls.stream()
                        .map(url -> ReviewImage.builder().review(review).url(url).build())
                        .toList();
        return reviewImageRepository.saveAll(images);
    }

    private List<ReviewKeyword> saveReviewKeywords(
            Review review, List<String> goodKeywords, List<String> badKeywords) {
        List<ReviewKeyword> keywords = new ArrayList<>();

        keywords.addAll(
                goodKeywords.stream()
                        .map(
                                keyword ->
                                        ReviewKeyword.builder()
                                                .review(review)
                                                .content(keyword)
                                                .isPositive(true)
                                                .build())
                        .toList());

        keywords.addAll(
                badKeywords.stream()
                        .map(
                                keyword ->
                                        ReviewKeyword.builder()
                                                .review(review)
                                                .content(keyword)
                                                .isPositive(false)
                                                .build())
                        .toList());

        return reviewKeywordRepository.saveAll(keywords);
    }

    public void calculateMemberLevel(final Member member) {
        final long memberReviewCnt = reviewRepository.countByUserId(member.getId());
        updateMemberUsecase.updateLevel(member, memberReviewCnt);
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
}
