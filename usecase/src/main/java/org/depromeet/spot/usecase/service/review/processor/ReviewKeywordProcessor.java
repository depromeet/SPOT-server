package org.depromeet.spot.usecase.service.review.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.depromeet.spot.usecase.port.out.review.KeywordRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewKeywordProcessor {

    private final KeywordRepository keywordRepository;
    private final BlockTopKeywordRepository blockTopKeywordRepository;

    public Map<Long, Keyword> processKeywords(
            Review review, List<String> goodKeywords, List<String> badKeywords) {
        Map<Long, Keyword> keywordMap = new HashMap<>();
        processKeywordList(review, goodKeywords, true, keywordMap);
        processKeywordList(review, badKeywords, false, keywordMap);
        return keywordMap;
    }

    // FIXME: 쿼리 호출 부분 개선 필요
    private void processKeywordList(
            Review review,
            List<String> keywordContents,
            boolean isPositive,
            Map<Long, Keyword> keywordMap) {
        for (String content : keywordContents) {
            Keyword keyword =
                    keywordRepository
                            .findByContent(content)
                            .orElseGet(
                                    () ->
                                            keywordRepository.save(
                                                    Keyword.create(null, content, isPositive)));

            ReviewKeyword reviewKeyword = ReviewKeyword.create(null, keyword.getId());
            review.addKeyword(reviewKeyword);
            keywordMap.put(keyword.getId(), keyword);
        }
    }

    public void updateBlockTopKeywords(Review review) {
        for (ReviewKeyword reviewKeyword : review.getKeywords()) {
            blockTopKeywordRepository.updateKeywordCount(
                    review.getBlock().getId(), reviewKeyword.getKeywordId());
        }
    }
}
