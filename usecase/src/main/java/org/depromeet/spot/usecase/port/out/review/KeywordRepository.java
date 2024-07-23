package org.depromeet.spot.usecase.port.out.review;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.depromeet.spot.domain.review.keyword.Keyword;

public interface KeywordRepository {

    Keyword save(Keyword keyword);

    Optional<Keyword> findByContent(String content);

    Map<Long, Keyword> findByIds(List<Long> keywordIds);
}
