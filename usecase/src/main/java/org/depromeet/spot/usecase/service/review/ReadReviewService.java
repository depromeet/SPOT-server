package org.depromeet.spot.usecase.service.review;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.Level;
import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.domain.review.image.TopReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase;
import org.depromeet.spot.usecase.port.out.member.MemberRepository;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            Pageable pageable) {

        // LocationInfo 조회
        LocationInfo locationInfo =
                reviewRepository.findLocationInfoByStadiumIdAndBlockCode(stadiumId, blockCode);

        // stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 reviews를 조회
        Page<Review> reviewPage =
                reviewRepository.findByStadiumIdAndBlockCode(
                        stadiumId, blockCode, rowNumber, seatNumber, year, month, pageable);

        //  stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topKeywords를 조회
        List<BlockKeywordInfo> topKeywords =
                blockTopKeywordRepository.findTopKeywordsByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_KEYWORDS_LIMIT);

        // stadiumId랑 blockCode로 blockId를 조회 후 이걸 통해 topImages를 조회
        List<TopReviewImage> topReviewImages =
                reviewImageRepository.findTopReviewImagesByStadiumIdAndBlockCode(
                        stadiumId, blockCode, TOP_IMAGES_LIMIT);

        List<Review> reviewsWithKeywords = mapKeywordsToReviews(reviewPage.getContent());

        return BlockReviewListResult.builder()
                .location(locationInfo)
                .reviews(reviewsWithKeywords)
                .topKeywords(topKeywords)
                .topReviewImages(topReviewImages)
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .number(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .build();
    }

    @Override
    public MyReviewListResult findMyReviewsByUserId(
            Long userId, Integer year, Integer month, Pageable pageable) {

        Page<Review> reviewPage = reviewRepository.findByUserId(userId, year, month, pageable);

        List<Review> reviewsWithKeywords = mapKeywordsToReviews(reviewPage.getContent());

        Member member = memberRepository.findById(userId);

        MemberInfoOnMyReviewResult memberInfo =
                createMemberInfoFromMember(member, reviewPage.getTotalElements());

        return MyReviewListResult.builder()
                .memberInfoOnMyReviewResult(memberInfo)
                .reviews(reviewsWithKeywords)
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .number(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .build();
    }

    @Override
    public List<ReviewYearMonth> findReviewMonths(Long memberId) {
        return reviewRepository.findReviewMonthsByMemberId(memberId);
    }

    private MemberInfoOnMyReviewResult createMemberInfoFromMember(
            Member member, long totalReviewCount) {
        return MemberInfoOnMyReviewResult.builder()
                .userId(member.getId())
                .profileImageUrl(member.getProfileImage())
                .level(member.getLevel())
                .levelTitle(Level.getTitleFrom(member.getLevel()))
                .nickname(member.getNickname())
                .reviewCount(totalReviewCount)
                .build();
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

    private List<Review> mapKeywordsToReviews(List<Review> reviews) {
        List<Long> keywordIds =
                reviews.stream()
                        .flatMap(review -> review.getKeywords().stream())
                        .map(ReviewKeyword::getKeywordId)
                        .distinct()
                        .collect(Collectors.toList());

        Map<Long, Keyword> keywordMap = keywordRepository.findByIds(keywordIds);

        return reviews.stream()
                .map(
                        review -> {
                            List<ReviewKeyword> mappedKeywords =
                                    review.getKeywords().stream()
                                            .map(
                                                    reviewKeyword -> {
                                                        Keyword keyword =
                                                                keywordMap.get(
                                                                        reviewKeyword
                                                                                .getKeywordId());
                                                        return ReviewKeyword.create(
                                                                reviewKeyword.getId(),
                                                                keyword.getId());
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

                            // Keyword 정보를 Review 객체에 추가
                            mappedReview.setKeywordMap(keywordMap);

                            return mappedReview;
                        })
                .collect(Collectors.toList());
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
