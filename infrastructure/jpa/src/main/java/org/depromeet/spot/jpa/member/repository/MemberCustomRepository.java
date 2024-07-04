package org.depromeet.spot.jpa.member.repository;

import static org.depromeet.spot.jpa.member.entity.QMemberEntity.memberEntity;

import java.util.List;

import org.depromeet.spot.jpa.member.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<MemberEntity> findByName(final String name) {
        return queryFactory.selectFrom(memberEntity).where(eqMemberName(name)).fetch();
    }

    private BooleanExpression eqMemberName(final String name) {
        if (name == null) {
            return null;
        }
        return memberEntity.name.eq(name);
    }
}
