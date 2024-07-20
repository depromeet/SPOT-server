package org.depromeet.spot.jpa.review.repository;

import static org.depromeet.spot.jpa.review.entity.QReviewEntity.reviewEntity;

import java.util.List;

import org.depromeet.spot.domain.review.Review;
import org.depromeet.spot.domain.review.ReviewYearMonth;
import org.depromeet.spot.jpa.review.entity.QReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ReviewCustomRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Page<Review> findByStadiumIdAndBlockCode(
            Long stadiumId,
            String blockCode,
            Integer rowNumber,
            Integer seatNumber,
            Integer year,
            Integer month,
            Pageable pageable) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QStadiumEntity stadium = QStadiumEntity.stadiumEntity;
        QSectionEntity section = QSectionEntity.sectionEntity;
        QBlockEntity block = QBlockEntity.blockEntity;
        QBlockRowEntity blockRow = QBlockRowEntity.blockRowEntity;
        QSeatEntity seat = QSeatEntity.seatEntity;

        List<Review> reviews =
                queryFactory
                        .selectFrom(review)
                        .join(review.member, member)
                        .fetchJoin()
                        .join(review.stadium, stadium)
                        .fetchJoin()
                        .join(review.section, section)
                        .fetchJoin()
                        .join(review.block, block)
                        .fetchJoin()
                        .join(review.row, blockRow)
                        .fetchJoin()
                        .join(review.seat, seat)
                        .fetchJoin()
                        .leftJoin(review.images)
                        .fetchJoin()
                        .leftJoin(review.keywords)
                        .fetchJoin()
                        .where(
                                review.stadium
                                        .id
                                        .eq(stadiumId)
                                        .and(review.block.code.eq(blockCode))
                                        .and(
                                                rowNumber != null
                                                        ? blockRow.number.eq(rowNumber)
                                                        : null)
                                        .and(
                                                seatNumber != null
                                                        ? seat.seatNumber.eq(seatNumber)
                                                        : null)
                                        .and(year != null ? review.dateTime.year().eq(year) : null)
                                        .and(
                                                month != null
                                                        ? review.dateTime.month().eq(month)
                                                        : null))
                        .distinct()
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
                        .stream()
                        .map(QReviewEntity::toDomain)
                        .collect(Collectors.toList());

        Long total =
                queryFactory
                        .select(review.countDistinct())
                        .from(review)
                        .where(
                                review.stadium
                                        .id
                                        .eq(stadiumId)
                                        .and(review.block.code.eq(blockCode))
                                        .and(
                                                rowNumber != null
                                                        ? review.row.number.eq(rowNumber)
                                                        : null)
                                        .and(
                                                seatNumber != null
                                                        ? review.seat.seatNumber.eq(seatNumber)
                                                        : null)
                                        .and(year != null ? review.dateTime.year().eq(year) : null)
                                        .and(
                                                month != null
                                                        ? review.dateTime.month().eq(month)
                                                        : null))
                        .fetchOne();

        return new PageImpl<>(reviews, pageable, total);
    }

    public Page<Review> findByUserId(Long userId, Integer year, Integer month, Pageable pageable) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QStadiumEntity stadium = QStadiumEntity.stadiumEntity;
        QSectionEntity section = QSectionEntity.sectionEntity;
        QBlockEntity block = QBlockEntity.blockEntity;
        QBlockRowEntity blockRow = QBlockRowEntity.blockRowEntity;
        QSeatEntity seat = QSeatEntity.seatEntity;

        List<Review> reviews =
                queryFactory
                        .selectFrom(review)
                        .join(review.member, member)
                        .fetchJoin()
                        .join(review.stadium, stadium)
                        .fetchJoin()
                        .join(review.section, section)
                        .fetchJoin()
                        .join(review.block, block)
                        .fetchJoin()
                        .join(review.row, blockRow)
                        .fetchJoin()
                        .join(review.seat, seat)
                        .fetchJoin()
                        .leftJoin(review.images)
                        .fetchJoin()
                        .leftJoin(review.keywords)
                        .fetchJoin()
                        .where(
                                review.member
                                        .id
                                        .eq(userId)
                                        .and(year != null ? review.dateTime.year().eq(year) : null)
                                        .and(
                                                month != null
                                                        ? review.dateTime.month().eq(month)
                                                        : null))
                        .distinct()
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(review.dateTime.desc())
                        .fetch()
                        .stream()
                        .map(QReviewEntity::toDomain)
                        .collect(Collectors.toList());

        Long total =
                queryFactory
                        .select(review.countDistinct())
                        .from(review)
                        .where(
                                review.member
                                        .id
                                        .eq(userId)
                                        .and(year != null ? review.dateTime.year().eq(year) : null)
                                        .and(
                                                month != null
                                                        ? review.dateTime.month().eq(month)
                                                        : null))
                        .fetchOne();

        return new PageImpl<>(reviews, pageable, total);
    }

    public List<ReviewYearMonth> findReviewMonthsByMemberId(Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ReviewYearMonth.class,
                                reviewEntity.dateTime.year(),
                                reviewEntity.dateTime.month()))
                .from(reviewEntity)
                .where(reviewEntity.member.id.eq(memberId))
                .groupBy(reviewEntity.dateTime.year(), reviewEntity.dateTime.month())
                .orderBy(reviewEntity.dateTime.year().desc(), reviewEntity.dateTime.month().desc())
                .fetch();
    }
}
