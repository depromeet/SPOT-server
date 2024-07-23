package org.depromeet.spot.jpa.review.repository.keyword;

import org.depromeet.spot.jpa.review.entity.keyword.BlockTopKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockTopKeywordJpaRepository extends JpaRepository<BlockTopKeywordEntity, Long> {
    @Modifying
    @Query(
            "UPDATE BlockTopKeywordEntity b SET b.count = b.count + 1, b.updatedAt = CURRENT_TIMESTAMP "
                    + "WHERE b.block.id = :blockId AND b.keyword.id = :keywordId")
    int incrementCount(Long blockId, Long keywordId);

    // JPA에서 ON Duplicate key update 구문을 지원하지 않음 -> native query 사용
    @Modifying
    @Query(
            value =
                    "INSERT INTO block_top_keywords (block_id, keyword_id, count, created_at, updated_at) "
                            + "VALUES (:blockId, :keywordId, 1, NOW(), NOW()) "
                            + "ON DUPLICATE KEY UPDATE count = count + 1, updated_at = NOW()",
            nativeQuery = true)
    void upsertCount(Long blockId, Long keywordId);
}
