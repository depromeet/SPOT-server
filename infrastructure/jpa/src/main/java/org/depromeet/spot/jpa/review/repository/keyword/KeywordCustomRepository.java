// package org.depromeet.spot.jpa.review.repository.keyword;
//
// import static org.depromeet.spot.jpa.review.entity.keyword.QKeywordEntity.keywordEntity;
//
// import java.util.Optional;
//
// import org.depromeet.spot.jpa.review.entity.keyword.KeywordEntity;
// import org.springframework.stereotype.Repository;
//
// import com.querydsl.jpa.impl.JPAQueryFactory;
//
// import lombok.RequiredArgsConstructor;
//
// @Repository
// @RequiredArgsConstructor
// public class KeywordCustomRepository {
//
//    private final JPAQueryFactory queryFactory;
//
//    public Optional<KeywordEntity> findByContent(String content) {
//        return Optional.ofNullable(
//            queryFactory
//                .selectFrom(keywordEntity)
//                .where(keywordEntity.content.eq(content))
//                .fetchOne()
//        );
//    }
// }
