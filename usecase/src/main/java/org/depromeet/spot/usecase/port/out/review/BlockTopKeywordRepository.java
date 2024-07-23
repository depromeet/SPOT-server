package org.depromeet.spot.usecase.port.out.review;

import java.util.List;

import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockKeywordInfo;

public interface BlockTopKeywordRepository {

    void updateKeywordCount(Long blockId, Long keywordId);

    List<BlockKeywordInfo> findTopKeywordsByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit);
}
