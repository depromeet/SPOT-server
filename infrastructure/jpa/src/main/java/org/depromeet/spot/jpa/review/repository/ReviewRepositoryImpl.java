package org.depromeet.spot.jpa.review.repository;

import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.image.ReviewImage;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.jpa.block.entity.BlockEntity;
import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.jpa.review.entity.image.ReviewImageEntity;
import org.depromeet.spot.jpa.review.entity.keyword.BlockTopKeywordEntity;
import org.depromeet.spot.jpa.review.entity.keyword.KeywordEntity;
import org.depromeet.spot.jpa.review.entity.keyword.ReviewKeywordEntity;
import org.depromeet.spot.usecase.port.out.review.ReviewRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Review save(Review review) {
        ReviewEntity entity = ReviewEntity.from(review);
        return reviewJpaRepository.save(entity).toDomain();
    }

    @Override
    public ReviewImage saveReviewImage(ReviewImage reviewImage) {
        ReviewImageEntity entity = ReviewImageEntity.from(reviewImage);
        entityManager.persist(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Keyword> findKeywordByContent(String content) {
        return reviewJpaRepository.findKeywordByContent(content).map(KeywordEntity::toDomain);
    }

    @Override
    public Keyword saveKeyword(Keyword keyword) {
        KeywordEntity entity = KeywordEntity.from(keyword);
        entityManager.persist(entity);
        return entity.toDomain();
    }

    @Override
    public ReviewKeyword saveReviewKeyword(ReviewKeyword reviewKeyword) {
        ReviewKeywordEntity entity = ReviewKeywordEntity.from(reviewKeyword);
        entityManager.persist(entity);
        return entity.toDomain();
    }

    @Override
    @Transactional
    public void updateBlockTopKeyword(Long blockId, Long keywordId) {
        int updatedCount = reviewJpaRepository.updateBlockTopKeyword(blockId, keywordId);
        if (updatedCount == 0 && !reviewJpaRepository.existsBlockTopKeyword(blockId, keywordId)) {
            BlockEntity blockEntity = entityManager.getReference(BlockEntity.class, blockId);
            KeywordEntity keywordEntity =
                    entityManager.getReference(KeywordEntity.class, keywordId);
            BlockTopKeywordEntity newEntity =
                    new BlockTopKeywordEntity(blockEntity, keywordEntity, 1L);
            entityManager.persist(newEntity);
        }
    }

    @Override
    public long countByUserId(Long userId) {
        return reviewJpaRepository.countByMemberId(userId);
    }
}
