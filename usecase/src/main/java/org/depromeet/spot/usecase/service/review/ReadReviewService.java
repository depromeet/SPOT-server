package org.depromeet.spot.usecase.service.review;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.common.exception.review.ReviewException.ReviewNotFoundException;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.image.TopReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadReviewService implements ReadReviewUsecase {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final BlockTopKeywordRepository blockTopKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final MemberRepository memberRepository;
    private final BaseballTeamRepository baseballTeamRepository;

    private static final int TOP_KEYWORDS_LIMIT = 5;
    private static final int TOP_IMAGES_LIMIT = 5;

    @Override
    public BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size) {

        // LocationInfo 조회
        LocationInfo locationInfo =
                reviewRepository.findLocationInfoByStadiumIdAndBlockCode(stadiumId, blockCode);

        // stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 reviews를 조회
        List<Review> reviews =
                reviewRepository.findByStadiumIdAndBlockCode(
                        stadiumId,
                        blockCode,
                        rowNumber,
                        seatNumber,
                        year,
                        month,
                        cursor,
                        sortBy,
                        size + 1);
        boolean hasNext = reviews.size() > size;
        if (hasNext) {
            reviews = reviews.subList(0, size);
        }

        String nextCursor = hasNext ? getCursor(reviews.get(reviews.size() - 1), sortBy) : null;

        //  stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topKeywords를 조회
        List<BlockKeywordInfo> topKeywords =
                blockTopKeywordRepository.findTopKeywordsByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_KEYWORDS_LIMIT);

        // stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topImages를 조회
        List<TopReviewImage> topReviewImages =
                reviewImageRepository.findTopReviewImagesByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_IMAGES_LIMIT);

        List<Review> reviewsWithKeywords = mapKeywordsToReviews(reviews);

        long totalElements =
                reviewRepository.countByStadiumIdAndBlockCode(
                        stadiumId, blockCode, rowNumber, seatNumber, year, month);

        return BlockReviewListResult.builder()
                .location(locationInfo)
                .reviews(reviewsWithKeywords)
                .topKeywords(topKeywords)
                .topReviewImages(topReviewImages)
                .totalElements(totalElements)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    @Override
    public MyReviewListResult findMyReviewsByUserId(
            Long userId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size) {

        List<Review> reviews =
                reviewRepository.findAllByUserId(userId, year, month, cursor, sortBy, size + 1);

        boolean hasNext = reviews.size() > size;
        if (hasNext) {
            reviews = reviews.subList(0, size);
        }

        String nextCursor = hasNext ? getCursor(reviews.get(reviews.size() - 1), sortBy) : null;

        List<Review> reviewsWithKeywords = mapKeywordsToReviews(reviews);

        Member member = memberRepository.findById(userId);

        MemberInfoOnMyReviewResult memberInfo;
        if (member.getTeamId() == null) {
            memberInfo =
                    MemberInfoOnMyReviewResult.of(member, reviewRepository.countByUserId(userId));

        } else {
            BaseballTeam baseballTeam = baseballTeamRepository.findById(member.getTeamId());

            memberInfo =
                    MemberInfoOnMyReviewResult.of(
                            member, reviewRepository.countByUserId(userId), baseballTeam.getName());
        }

        return MyReviewListResult.builder()
                .memberInfoOnMyReviewResult(memberInfo)
                .reviews(reviewsWithKeywords)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    private String getCursor(Review review, SortCriteria sortBy) {
        switch (sortBy) {
                // TODO: 좋아요 컬럼 반영 시 주석 해제
                //            case LIKES_COUNT:
                //                return review.getLikesCount() + "_" +
                // review.getDateTime().toString() + "_" + review.getId();
            case DATE_TIME:
            default:
                return review.getDateTime().toString() + "_" + review.getId();
        }
    }

    @Override
    public List<ReviewYearMonth> findReviewMonths(Long memberId) {
        return reviewRepository.findReviewMonthsByMemberId(memberId);
    }

    @Override
    public ReadReviewResult findReviewById(Long reviewId) {
        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(
                                () ->
                                        new ReviewNotFoundException(
                                                "Review not found with id: " + reviewId));
        Review reviewWithKeywords = mapKeywordsToSingleReview(review);

        return ReadReviewResult.builder().review(reviewWithKeywords).build();
    }

    @Override
    public long countByIdByMemberId(Long memberId) {
        return reviewRepository.countByIdByMemberId(memberId);
    }

    @Override
    public long countByMember(Long memberId) {
        return reviewRepository.countByUserId(memberId);
    }

    @Override
    public MyRecentReviewResult findLastReviewByMemberId(Long memberId) {
        Review review = reviewRepository.findLastReviewByMemberId(memberId);

        long reviewCount = reviewRepository.countByIdByMemberId(memberId);

        Review reviewWithKeywords = mapKeywordsToReview(review);

        return MyRecentReviewResult.builder()
                .review(reviewWithKeywords)
                .reviewCount(reviewCount)
                .build();
    }

    private Review mapKeywordsToSingleReview(Review review) {
        List<Long> keywordIds =
                review.getKeywords().stream()
                        .map(ReviewKeyword::getKeywordId)
                        .distinct()
                        .collect(Collectors.toList());

        Map<Long, Keyword> keywordMap = keywordRepository.findByIds(keywordIds);

        List<ReviewKeyword> mappedKeywords =
                review.getKeywords().stream()
                        .map(
                                reviewKeyword -> {
                                    Keyword keyword = keywordMap.get(reviewKeyword.getKeywordId());
                                    return ReviewKeyword.create(
                                            reviewKeyword.getId(), keyword.getId());
                                })
                        .collect(Collectors.toList());

        Review mappedReview =
                Review.builder()
                        .id(review.getId())
                        .member(review.getMember())
                        .stadium(review.getStadium())
                        .section(review.getSection())
                        .block(review.getBlock())
                        .row(review.getRow())
                        .seat(review.getSeat())
                        .dateTime(review.getDateTime())
                        .content(review.getContent())
                        .deletedAt(review.getDeletedAt())
                        .images(review.getImages())
                        .keywords(mappedKeywords)
                        .build();

        mappedReview.setKeywordMap(keywordMap);

        return mappedReview;
    }

    private List<Review> mapKeywordsToReviews(List<Review> reviews) {
        return reviews.stream().map(this::mapKeywordsToSingleReview).collect(Collectors.toList());
    }

    private Review mapKeywordsToReview(Review review) {
        // TODO : (민성) 중복되는 Keywords 로직 처리 부분 메소드로 분리하기!
        List<Long> keywordIds =
                review.getKeywords().stream()
                        .map(ReviewKeyword::getKeywordId)
                        .distinct()
                        .collect(Collectors.toList());

        Map<Long, Keyword> keywordMap = keywordRepository.findByIds(keywordIds);
        List<ReviewKeyword> mappedKeywords =
                review.getKeywords().stream()
                        .map(
                                reviewKeyword -> {
                                    Keyword keyword = keywordMap.get(reviewKeyword.getKeywordId());
                                    return ReviewKeyword.create(
                                            reviewKeyword.getId(), keyword.getId());
                                })
                        .collect(Collectors.toList());

        Review mappedReview =
                Review.builder()
                        .id(review.getId())
                        .member(review.getMember())
                        .stadium(review.getStadium())
                        .section(review.getSection())
                        .block(review.getBlock())
                        .row(review.getRow())
                        .seat(review.getSeat())
                        .dateTime(review.getDateTime())
                        .content(review.getContent())
                        .deletedAt(review.getDeletedAt())
                        .images(review.getImages())
                        .keywords(mappedKeywords) // 리뷰 키워드 담당
                        .build();

        // Keyword 정보를 Review 객체에 추가
        // -> 리뷰에서 내에서 키워드가 map 형태가 아닌 List 형태로 되어 있음!
        mappedReview.setKeywordMap(keywordMap);

        return mappedReview;
    }
}
