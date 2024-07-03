package org.depromeet.spot.jpa.member.entity;

/* JPA 설정 확인용 샘플 엔티티. 실제 피처 개발 시작할 때 삭제 예정! */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.member.Member;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public MemberEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MemberEntity from(Member member) {
        return new MemberEntity(member.getId(), member.getName());
    }

    public Member toDomain() {
        return new Member(id, name);
    }
}
