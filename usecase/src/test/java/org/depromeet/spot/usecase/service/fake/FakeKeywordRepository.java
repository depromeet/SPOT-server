package org.depromeet.spot.usecase.service.fake;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;

// FIXME: 구현 필요
public class FakeKeywordRepository implements KeywordRepository {

    @Override
    public Keyword save(Keyword keyword) {
        return null;
    }

    @Override
    public Optional<Keyword> findByContent(String content) {
        return Optional.empty();
    }

    @Override
    public Map<Long, Keyword> findByIds(List<Long> keywordIds) {
        return null;
    }
}
