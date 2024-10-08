package org.depromeet.spot.usecase.service.review;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.Review.ReviewType;
import org.depromeet.spot.domain.review.Review.SortCriteria;
import org.depromeet.spot.domain.review.ReviewCount;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewLikeRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewScrapRepository;
import org.depromeet.spot.usecase.port.out.team.BaseballTeamRepository;
import org.depromeet.spot.usecase.service.review.processor.PaginationProcessor;
import org.depromeet.spot.usecase.service.review.processor.ReadReviewProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadReviewService implements ReadReviewUsecase {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final BlockTopKeywordRepository blockTopKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final MemberRepository memberRepository;
    private final BaseballTeamRepository baseballTeamRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewScrapRepository reviewScrapRepository;
    private final ReadReviewProcessor readReviewProcessor;
    private final PaginationProcessor paginationProcessor;

    private static final int TOP_KEYWORDS_LIMIT = 5;
    private static final int TOP_IMAGES_LIMIT = 5;

    @Override
    public BlockReviewListResult findReviewsByStadiumIdAndBlockCode(
            Long memberId,
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

        String nextCursor =
                hasNext
                        ? paginationProcessor.getCursor(reviews.get(reviews.size() - 1), sortBy)
                        : null;

        //  stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topKeywords를 조회
        List<BlockKeywordInfo> topKeywords =
                blockTopKeywordRepository.findTopKeywordsByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_KEYWORDS_LIMIT);

        // stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topImages를 조회
        List<Review> topReviewImages =
                reviewImageRepository.findTopReviewImagesByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_IMAGES_LIMIT);

        List<Review> topReviewImagesWithKeywords = mapKeywordsToReviews(topReviewImages);

        List<Review> reviewsWithKeywords = mapKeywordsToReviews(reviews);

        // 유저의 리뷰 좋아요, 스크랩 여부
        readReviewProcessor.setLikedAndScrappedStatus(reviewsWithKeywords, memberId);

        long totalElements =
                reviewRepository.countByStadiumIdAndBlockCode(
                        stadiumId, blockCode, rowNumber, seatNumber, year, month);

        return BlockReviewListResult.builder()
                .location(locationInfo)
                .reviews(reviewsWithKeywords)
                .topKeywords(topKeywords)
                .topReviewImages(topReviewImagesWithKeywords)
                .totalElements(totalElements)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    @Override
    public MemberInfoOnMyReviewResult findMemberInfoOnMyReview(Long memberId) {
        Member member = memberRepository.findById(memberId);
        ReviewCount reviewCount = reviewRepository.countAndSumLikesByUserId(memberId);

        if (member.getTeamId() == null) {
            return MemberInfoOnMyReviewResult.of(member, reviewCount);
        } else {
            BaseballTeam baseballTeam = baseballTeamRepository.findById(member.getTeamId());
            return MemberInfoOnMyReviewResult.of(member, reviewCount, baseballTeam.getName());
        }
    }

    @Override
    public MyReviewListResult findMyReviewsByUserId(
            Long memberId,
            Integer year,
            Integer month,
            String cursor,
            SortCriteria sortBy,
            Integer size,
            ReviewType reviewType) {

        if (reviewType == null) {
            reviewType = ReviewType.VIEW;
        }

        List<Review> reviews =
                reviewRepository.findAllByUserId(
                        memberId, year, month, cursor, sortBy, size + 1, reviewType);

        boolean hasNext = reviews.size() > size;
        if (hasNext) {
            reviews = reviews.subList(0, size);
        }

        String nextCursor =
                hasNext
                        ? paginationProcessor.getCursor(reviews.get(reviews.size() - 1), sortBy)
                        : null;

        List<Review> reviewsWithKeywords = mapKeywordsToReviews(reviews);

        if (reviewType.equals(ReviewType.VIEW)) {
            // 유저의 리뷰 좋아요, 스크랩 여부
            readReviewProcessor.setLikedAndScrappedStatus(reviewsWithKeywords, memberId);
        }

        return MyReviewListResult.builder()
                .reviews(reviewsWithKeywords)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    @Override
    public List<ReviewYearMonth> findReviewMonths(Long memberId, ReviewType reviewType) {
        if (reviewType == null) {
            reviewType = ReviewType.VIEW;
        }
        return reviewRepository.findReviewMonthsByMemberId(memberId, reviewType);
    }

    @Override
    public ReadReviewResult findReviewById(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId);
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
    public Review findById(long reviewId) {
        return reviewRepository.findById(reviewId);
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

    public Review mapKeywordsToSingleReview(Review review) {
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
                        .likesCount(review.getLikesCount())
                        .scrapsCount(review.getScrapsCount())
                        .reviewType(review.getReviewType())
                        .viewsCount(review.getViewsCount())
                        .build();

        mappedReview.setKeywordMap(keywordMap);

        return mappedReview;
    }

    public List<Review> mapKeywordsToReviews(List<Review> reviews) {
        return reviews.stream().map(this::mapKeywordsToSingleReview).collect(Collectors.toList());
    }

    public Review mapKeywordsToReview(Review review) {
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
                        .likesCount(review.getLikesCount())
                        .scrapsCount(review.getScrapsCount())
                        .reviewType(review.getReviewType())
                        .viewsCount(review.getViewsCount())
                        .build();

        // Keyword 정보를 Review 객체에 추가
        // -> 리뷰에서 내에서 키워드가 map 형태가 아닌 List 형태로 되어 있음!
        mappedReview.setKeywordMap(keywordMap);

        return mappedReview;
    }
}
