package org.depromeet.spot.usecase.service.fake;

import java.util.List;

import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockKeywordInfo;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;

// FIXME: 구현 필요
public class FakeBlockTopKeywordRepository implements BlockTopKeywordRepository {

    @Override
    public void updateKeywordCount(Long blockId, Long keywordId) {}

    @Override
    public void batchUpdateCounts(Long blockId, List<Long> incrementIds, List<Long> decrementIds) {}

    @Override
    public List<BlockKeywordInfo> findTopKeywordsByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return null;
    }
}
