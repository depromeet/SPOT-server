package org.depromeet.spot.jpa.review.repository;

import java.util.Optional;

import org.depromeet.spot.jpa.review.entity.ReviewEntity;
import org.depromeet.spot.jpa.review.entity.keyword.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    long countByMemberId(Long memberId);

    @Query("SELECT k FROM KeywordEntity k WHERE k.content = :content")
    Optional<KeywordEntity> findKeywordByContent(@Param("content") String content);

    @Modifying
    @Query(
            "UPDATE BlockTopKeywordEntity btk SET btk.count = btk.count + 1 "
                    + "WHERE btk.block.id = :blockId AND btk.keyword.id = :keywordId")
    int updateBlockTopKeyword(@Param("blockId") Long blockId, @Param("keywordId") Long keywordId);

    @Query(
            "SELECT COUNT(btk) > 0 FROM BlockTopKeywordEntity btk "
                    + "WHERE btk.block.id = :blockId AND btk.keyword.id = :keywordId")
    boolean existsBlockTopKeyword(
            @Param("blockId") Long blockId, @Param("keywordId") Long keywordId);
}
