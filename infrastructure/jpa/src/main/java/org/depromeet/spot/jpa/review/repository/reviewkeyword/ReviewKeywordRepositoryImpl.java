package org.depromeet.spot.jpa.review.repository.reviewkeyword;

import java.util.List;
import java.util.stream.Collectors;

import org.depromeet.spot.domain.review.ReviewKeyword;
import org.depromeet.spot.domain.review.result.KeywordCount;
import org.depromeet.spot.jpa.review.entity.ReviewKeywordEntity;
import org.depromeet.spot.jpa.review.repository.ReviewKeywordCustomRepository;
import org.depromeet.spot.usecase.port.out.review.ReviewKeywordRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewKeywordRepositoryImpl implements ReviewKeywordRepository {
    private final ReviewKeywordJpaRepository reviewKeywordJpaRepository;
    private final ReviewKeywordCustomRepository reviewKeywordCustomRepository;

    @Override
    public List<KeywordCount> findTopKeywordsByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return reviewKeywordCustomRepository.findTopKeywordsByStadiumIdAndBlockCode(
                stadiumId, blockCode, limit);
    }

    @Override
    public List<ReviewKeyword> saveAll(List<ReviewKeyword> reviewKeywords) {
        List<ReviewKeywordEntity> entities =
                reviewKeywords.stream().map(ReviewKeywordEntity::from).collect(Collectors.toList());
        List<ReviewKeywordEntity> savedEntities = reviewKeywordJpaRepository.saveAll(entities);
        return savedEntities.stream()
                .map(ReviewKeywordEntity::toDomain)
                .collect(Collectors.toList());
    }
}
