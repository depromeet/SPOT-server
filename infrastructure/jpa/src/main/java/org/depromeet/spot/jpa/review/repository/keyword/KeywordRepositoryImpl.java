package org.depromeet.spot.jpa.review.repository.keyword;

import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.jpa.review.entity.keyword.KeywordEntity;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class KeywordRepositoryImpl implements KeywordRepository {

    private final EntityManager entityManager;
    private final KeywordJpaRepository keywordJpaRepository;

    @Override
    public Keyword save(Keyword keyword) {
        KeywordEntity entity = KeywordEntity.from(keyword);
        return keywordJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Keyword> findByContent(String content) {
        return keywordJpaRepository.findByContent(content).map(KeywordEntity::toDomain);
    }
}
