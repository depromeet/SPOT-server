package org.depromeet.spot.infrastructure.jpa.review.repository.keyword;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;

import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.infrastructure.jpa.review.entity.keyword.KeywordEntity;
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

    @Override
    public Map<Long, Keyword> findByIds(List<Long> keywordIds) {
        return keywordJpaRepository.findAllById(keywordIds).stream()
                .map(KeywordEntity::toDomain)
                .collect(Collectors.toMap(Keyword::getId, Function.identity()));
    }
}
