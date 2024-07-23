package org.depromeet.spot.jpa.review.repository.keyword;

import java.util.Optional;

import org.depromeet.spot.jpa.review.entity.keyword.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordJpaRepository extends JpaRepository<KeywordEntity, Long> {
    Optional<KeywordEntity> findByContent(String content);
}
