package org.depromeet.spot.usecase.port.out.review;

public interface BlockTopKeywordRepository {
    void updateKeywordCount(Long blockId, Long keywordId);
}
