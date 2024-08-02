package org.depromeet.spot.jpa.review.repository.keyword;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.depromeet.spot.usecase.port.in.review.ReadReviewUsecase.BlockKeywordInfo;
import org.depromeet.spot.usecase.port.out.review.BlockTopKeywordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BlockTopKeywordRepositoryImpl implements BlockTopKeywordRepository {

    private final BlockTopKeywordJpaRepository blockTopKeywordJpaRepository;

    @Override
    @Transactional
    public void batchUpdateCounts(Long blockId, List<Long> incrementIds, List<Long> decrementIds) {
        List<Long> allIds =
                Stream.concat(incrementIds.stream(), decrementIds.stream())
                        .distinct()
                        .collect(Collectors.toList());

        if (!allIds.isEmpty()) {
            int updatedRows =
                    blockTopKeywordJpaRepository.batchUpdateCounts(
                            blockId, incrementIds, decrementIds, allIds);
            log.debug("Batch update performed. Rows affected: {}", updatedRows);
        }
    }

    //    @Override
    //    @Transactional
    //    public void updateKeywordCount(Long blockId, Long keywordId) {
    //        log.debug("Updating block top keyword: blockId={}, keywordId={}", blockId, keywordId);
    //        int updatedRows = blockTopKeywordJpaRepository.incrementCount(blockId, keywordId);
    //        log.debug("Rows updated by incrementCount: {}", updatedRows);
    //        if (updatedRows == 0) {
    //            blockTopKeywordJpaRepository.upsertCount(blockId, keywordId);
    //            log.debug("Performed upsert operation");
    //        }
    //    }
    //
    //    @Override
    //    @Transactional
    //    public void decrementCount(Long blockId, Long keywordId) {
    //        log.debug(
    //                "Decrementing block top keyword count: blockId={}, keywordId={}",
    //                blockId,
    //                keywordId);
    //        int updatedRows = blockTopKeywordJpaRepository.decrementCount(blockId, keywordId);
    //        log.debug("Rows updated by decrementCount: {}", updatedRows);
    //        if (updatedRows == 0) {
    //            log.debug(
    //                    "No rows updated, possibly because count was already 0 or entry doesn't
    // exist");
    //        }
    //    }

    @Override
    public List<BlockKeywordInfo> findTopKeywordsByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {
        return blockTopKeywordJpaRepository
                .findTopKeywordsByStadiumIdAndBlockCode(
                        stadiumId, blockCode, PageRequest.of(0, limit))
                .stream()
                .map(dto -> new BlockKeywordInfo(dto.content(), dto.count(), dto.isPositive()))
                .collect(Collectors.toList());
    }
}
