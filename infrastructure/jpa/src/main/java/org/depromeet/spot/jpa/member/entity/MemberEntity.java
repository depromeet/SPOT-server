package org.depromeet.spot.jpa.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.jpa.common.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseEntity {

    // TODO : email 받아온 후 nullable = false로 바꿔야함.
    @Column(name = "email", unique = true, length = 50)
    private String email;

    // TODO : 이름 받아온 후 nullable = false로 바꿔야함.
    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true, length = 10)
    private String nickname;

    // TODO : phone_number 받아온 후 nullable = false로 바꿔야함.
    @Column(name = "phone_number", unique = true, length = 13)
    private String phoneNumber;

    // TODO : ERD nullable로 변경
    @Column(name = "level")
    @ColumnDefault("1")
    private Integer level;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "sns_provider", nullable = false, length = 20)
    private String snsProvider;

    @Column(name = "id_token", nullable = false, unique = true, length = 255)
    private String idToken;

    @Column(name = "team_id", nullable = false, length = 10)
    private Long teamId;

    // TODO : 얘는 테이블 차이로 인한 임시 필드이므로 추후 수정해서 삭제해야함.
    @Column(name = "my_team", nullable = false, length = 10)
    private Long myTeam;

    @Column(name = "role", nullable = false)
    private String role;

    public static MemberEntity from(Member member) {
        return new MemberEntity(
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPhoneNumber(),
                // TODO : 레벨 - 추 후 PrePersist, DynamicInsert 등을 통해 기본값 넣기.
                1,
                member.getProfileImage(),
                member.getSnsProvider().getValue(),
                member.getIdToken(),
                member.getTeamId(),
                // TODO : 얘는 테이블 차이로 인한 임시 필드이므로 추후 수정해서 삭제해야함.
                member.getTeamId(),
                member.getRole().getValue());
    }

    public Member toDomain() {
        return new Member(
                this.getId(),
                email,
                name,
                nickname,
                phoneNumber,
                level,
                profileImage,
                SnsProvider.valueOf(snsProvider),
                idToken,
                teamId,
                MemberRole.valueOf(role),
                this.getCreatedAt(),
                this.getDeletedAt());
    }
}
