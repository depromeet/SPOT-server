package org.depromeet.spot.usecase.service.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.review.ReviewException.ReviewNotFoundException;
import org.depromeet.spot.common.exception.review.ReviewException.UnauthorizedReviewModificationException;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.seat.Seat;
import org.depromeet.spot.usecase.port.in.review.UpdateReviewUsecase;
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
@Transactional
public class UpdateReviewService implements UpdateReviewUsecase {

    private final ReviewRepository reviewRepository;
    private final SeatRepository seatRepository;
    private final KeywordRepository keywordRepository;
    private final BlockTopKeywordRepository blockTopKeywordRepository;

    public UpdateReviewResult updateReview(
            Long memberId, Long reviewId, UpdateReviewCommand command) {
        Review existingReview =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(
                                () -> new ReviewNotFoundException("요청한 리뷰를 찾을 수 없습니다." + reviewId));

        if (!existingReview.getMember().getId().equals(memberId)) {
            throw new UnauthorizedReviewModificationException();
        }

        Member member = existingReview.getMember();
        Seat seat = seatRepository.findByIdWith(command.blockId(), command.seatNumber());

        // 새로운 Review 객체 생성
        Review updatedReview = createUpdatedReview(reviewId, member, seat, command);

        // keyword와 image 처리
        Map<Long, Keyword> keywordMap =
                processKeywords(updatedReview, command.good(), command.bad());
        processImages(updatedReview, command.images());

        // 저장 및 blockTopKeyword 업데이트
        Review savedReview = reviewRepository.save(updatedReview);
        updateBlockTopKeywords(existingReview, savedReview);

        savedReview.setKeywordMap(keywordMap);

        return new UpdateReviewResult(savedReview);
    }

    private Review createUpdatedReview(
            Long reviewId, Member member, Seat seat, UpdateReviewCommand command) {
        return Review.builder()
                .id(reviewId)
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

    private Map<Long, Keyword> processKeywords(
            Review review, List<String> goodKeywords, List<String> badKeywords) {
        Map<Long, Keyword> keywordMap = new HashMap<>();
        processKeywordList(review, goodKeywords, true, keywordMap);
        processKeywordList(review, badKeywords, false, keywordMap);
        return keywordMap;
    }

    private void processKeywordList(
            Review review,
            List<String> keywordContents,
            boolean isPositive,
            Map<Long, Keyword> keywordMap) {
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
            keywordMap.put(keyword.getId(), keyword);
        }
    }

    private void processImages(Review review, List<String> imageUrls) {
        for (String url : imageUrls) {
            ReviewImage image = ReviewImage.create(null, review, url);
            review.addImage(image);
        }
    }

    private void updateBlockTopKeywords(Review oldReview, Review newReview) {
        Set<Long> oldKeywordIds =
                oldReview.getKeywords().stream()
                        .map(ReviewKeyword::getKeywordId)
                        .collect(Collectors.toSet());
        Set<Long> newKeywordIds =
                newReview.getKeywords().stream()
                        .map(ReviewKeyword::getKeywordId)
                        .collect(Collectors.toSet());

        List<Long> decrementIds =
                oldKeywordIds.stream()
                        .filter(id -> !newKeywordIds.contains(id))
                        .collect(Collectors.toList());

        List<Long> incrementIds =
                newKeywordIds.stream()
                        .filter(id -> !oldKeywordIds.contains(id))
                        .collect(Collectors.toList());

        if (!decrementIds.isEmpty() || !incrementIds.isEmpty()) {
            blockTopKeywordRepository.batchUpdateCounts(
                    newReview.getBlock().getId(), incrementIds, decrementIds);
        }
    }

    //    private void updateBlockTopKeywords(Review oldReview, Review newReview) {
    //        Set<Long> oldKeywordIds =
    //                oldReview.getKeywords().stream()
    //                        .map(ReviewKeyword::getKeywordId)
    //                        .collect(Collectors.toSet());
    //        Set<Long> newKeywordIds =
    //                newReview.getKeywords().stream()
    //                        .map(ReviewKeyword::getKeywordId)
    //                        .collect(Collectors.toSet());
    //
    //        oldKeywordIds.stream()
    //                .filter(id -> !newKeywordIds.contains(id))
    //                .forEach(
    //                        id ->
    //                                blockTopKeywordRepository.decrementCount(
    //                                        oldReview.getBlock().getId(), id));
    //
    //        newKeywordIds.stream()
    //                .filter(id -> !oldKeywordIds.contains(id))
    //                .forEach(
    //                        id ->
    //                                blockTopKeywordRepository.updateKeywordCount(
    //                                        newReview.getBlock().getId(), id));
    //    }
}
