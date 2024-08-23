package org.depromeet.spot.infrastructure.jpa.review.repository.image;

import static org.depromeet.spot.infrastructure.jpa.block.entity.QBlockEntity.blockEntity;
import static org.depromeet.spot.infrastructure.jpa.block.entity.QBlockRowEntity.blockRowEntity;
import static org.depromeet.spot.infrastructure.jpa.member.entity.QLevelEntity.levelEntity;
import static org.depromeet.spot.infrastructure.jpa.member.entity.QMemberEntity.memberEntity;
import static org.depromeet.spot.infrastructure.jpa.review.entity.QReviewEntity.reviewEntity;
import static org.depromeet.spot.infrastructure.jpa.seat.entity.QSeatEntity.seatEntity;
import static org.depromeet.spot.infrastructure.jpa.section.entity.QSectionEntity.sectionEntity;
import static org.depromeet.spot.infrastructure.jpa.stadium.entity.QStadiumEntity.stadiumEntity;

import java.util.List;

import org.depromeet.spot.infrastructure.jpa.review.entity.ReviewEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewImageCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<ReviewEntity> findTopReviewsWithImagesByStadiumIdAndBlockCode(
            Long stadiumId, String blockCode, int limit) {

        return queryFactory
                .selectFrom(reviewEntity)
                .leftJoin(reviewEntity.stadium, stadiumEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.section, sectionEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.block, blockEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.row, blockRowEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.seat, seatEntity)
                .fetchJoin()
                .leftJoin(reviewEntity.member, memberEntity)
                .fetchJoin()
                .leftJoin(memberEntity.level, levelEntity)
                .fetchJoin()
                .where(
                        reviewEntity
                                .stadium
                                .id
                                .eq(stadiumId)
                                .and(reviewEntity.block.code.eq(blockCode))
                                .and(reviewEntity.images.isNotEmpty())
                                .and(reviewEntity.deletedAt.isNull()))
                .orderBy(reviewEntity.likesCount.desc(), reviewEntity.dateTime.desc())
                .limit(limit)
                .fetch();
    }
}
