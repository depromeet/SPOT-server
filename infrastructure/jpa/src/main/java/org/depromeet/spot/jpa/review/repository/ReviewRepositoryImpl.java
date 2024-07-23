package org.depromeet.spot.jpa.review.repository;

import java.util.List;
import java.util.Optional;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity entity = ReviewEntity.from(review);
        ReviewEntity savedEntity = reviewJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewJpaRepository.findById(id).map(ReviewEntity::toDomain);
    }

    @Override
    public long countByUserId(Long id) {
        return reviewJpaRepository.countByMemberId(id);
    }

    @Override
    public Page<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable) {
        Page<ReviewEntity> reviewEntities =
                reviewJpaRepository.findByStadiumIdAndBlockCode(
                        stadiumId, blockCode, rowNumber, seatNumber, year, month, pageable);
        return reviewEntities.map(ReviewEntity::toDomain);
    }

    @Override
    public Page<Review> findByUserId(Long userId, Integer year, Integer month, Pageable pageable) {
        Page<ReviewEntity> reviewEntities =
                reviewJpaRepository.findByUserId(userId, year, month, pageable);
        return reviewEntities.map(ReviewEntity::toDomain);
    }

    @Override
    public List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId) {
        return reviewJpaRepository.findReviewMonthsByMemberId(memberId);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewJpaRepository.softDeleteById(reviewId);
    }

    //    @Override
    //    public ReviewImage saveReviewImage(ReviewImage reviewImage) {
    //        ReviewImageEntity entity = ReviewImageEntity.from(reviewImage);
    //        entityManager.persist(entity);
    //        return entity.toDomain();
    //    }
    //
    //    @Override
    //    public Optional<Keyword> findKeywordByContent(String content) {
    //        return reviewJpaRepository.findKeywordByContent(content).map(KeywordEntity::toDomain);
    //    }
    //
    //    @Override
    //    public Keyword saveKeyword(Keyword keyword) {
    //        KeywordEntity entity = KeywordEntity.from(keyword);
    //        entityManager.persist(entity);
    //        return entity.toDomain();
    //    }
    //
    //    @Override
    //    public ReviewKeyword saveReviewKeyword(ReviewKeyword reviewKeyword) {
    //        ReviewKeywordEntity entity = ReviewKeywordEntity.from(reviewKeyword);
    //        entityManager.persist(entity);
    //        return entity.toDomain();
    //    }

    //    @Override
    //    @Transactional
    //    public void updateBlockTopKeyword(Long blockId, Long keywordId) {
    //        int updatedCount = reviewJpaRepository.updateBlockTopKeyword(blockId, keywordId);
    //        if (updatedCount == 0 && !reviewJpaRepository.existsBlockTopKeyword(blockId,
    // keywordId)) {
    //            BlockEntity blockEntity = entityManager.getReference(BlockEntity.class, blockId);
    //            KeywordEntity keywordEntity =
    //                    entityManager.getReference(KeywordEntity.class, keywordId);
    //            BlockTopKeywordEntity newEntity =
    //                    new BlockTopKeywordEntity(blockEntity, keywordEntity, 1L);
    //            entityManager.persist(newEntity);
    //        }
    //    }
    //
    ////    @Override
    ////    public long countByUserId(Long userId) {
    ////        return reviewJpaRepository.countByMemberId(userId);
    ////    }
    //
    //    @Override
    //    @Transactional
    //    public Review addImages(Long reviewId, List<ReviewImage> images) {
    //        ReviewEntity reviewEntity = reviewJpaRepository.findById(reviewId).orElseThrow();
    //        images.forEach(image -> {
    //            ReviewImageEntity imageEntity = ReviewImageEntity.from(image, reviewEntity);
    //            reviewEntity.addImage(imageEntity);
    //        });
    //        return reviewJpaRepository.save(reviewEntity).toDomain();
    //    }
    //
    //    @Override
    //    @Transactional
    //    public Review addKeywords(Long reviewId, List<ReviewKeyword> reviewKeywords) {
    //        ReviewEntity reviewEntity = reviewJpaRepository.findById(reviewId).orElseThrow();
    //
    //        reviewKeywords.forEach(reviewKeyword -> {
    //            Keyword keyword = reviewKeyword.getKeyword();
    //            KeywordEntity keywordEntity =
    // keywordRepository.findByContent(keyword.getContent())
    //                .orElseGet(() -> {
    //                    KeywordEntity newKeywordEntity = KeywordEntity.from(keyword);
    //                    return entityManager.merge(newKeywordEntity); // 새로운 키워드 엔티티를 저장
    //                });
    //
    //            ReviewKeywordEntity reviewKeywordEntity = new ReviewKeywordEntity();
    //            reviewKeywordEntity.setReview(reviewEntity);
    //            reviewKeywordEntity.setKeyword(keywordEntity);
    //
    //            reviewEntity.addReviewKeyword(reviewKeywordEntity);
    //        });
    //
    //        return reviewJpaRepository.save(reviewEntity).toDomain();
    //    }

}
