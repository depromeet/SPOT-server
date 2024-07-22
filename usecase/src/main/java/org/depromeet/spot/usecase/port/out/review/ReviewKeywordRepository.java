package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.domain.review.result.KeywordCount;

public interface ReviewKeywordRepository {
    List<KeywordCount> findTopKeywordsByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit);

    List<ReviewKeyword> saveAll(List<ReviewKeyword> reviewKeywords);
}
