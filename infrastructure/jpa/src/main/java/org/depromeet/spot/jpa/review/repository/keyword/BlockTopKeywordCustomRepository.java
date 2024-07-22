// package org.depromeet.spot.jpa.review.repository.keyword;
// import static
// org.depromeet.spot.jpa.review.entity.keyword.QBlockTopKeywordEntity.blockTopKeywordEntity;
//
//
// import org.depromeet.spot.jpa.review.entity.keyword.BlockTopKeywordEntity;
// import org.springframework.stereotype.Repository;
//
// import com.querydsl.jpa.impl.JPAQueryFactory;
//
// import lombok.RequiredArgsConstructor;
//
// @Repository
// @RequiredArgsConstructor
// public class BlockTopKeywordCustomRepository {
//
//    private final JPAQueryFactory queryFactory;
//
//    public void updateOrInsertBlockTopKeyword(Long blockId, Long keywordId) {
//        BlockTopKeywordEntity existingEntity = queryFactory
//            .selectFrom(blockTopKeywordEntity)
//            .where(blockTopKeywordEntity.block.id.eq(blockId)
//                .and(blockTopKeywordEntity.keyword.id.eq(keywordId)))
//            .fetchOne();
//
//        if (existingEntity != null) {
//            queryFactory
//                .update(blockTopKeywordEntity)
//                .set(blockTopKeywordEntity.count, blockTopKeywordEntity.count.add(1))
//                .where(blockTopKeywordEntity.eq(existingEntity))
//                .execute();
//        } else {
//            BlockTopKeywordEntity newEntity = new BlockTopKeywordEntity(null, blockId, keywordId,
// 1L);
//            // JPA repository를 통해 저장
//        }
//    }
// }
