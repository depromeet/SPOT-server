package org.depromeet.spot.jpa.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.jpa.common.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseEntity {

    // TODO : email 받아온 후 nullable = false로 바꿔야함.
    @Column(name = "email", nullable = true, unique = true, length = 50)
    private String email;

    // TODO : 이름 받아온 후 nullable = false로 바꿔야함.
    @Column(name = "name", nullable = true, length = 20)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true, length = 10)
    private String nickname;

    // TODO : phone_number 받아온 후 nullable = false로 바꿔야함.
    @Column(name = "phone_number", nullable = true, unique = true, length = 13)
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

    @Column(name = "role", nullable = false)
    private String role;

    public static MemberEntity from(Member member) {
        return new MemberEntity(
            member.getEmail(),
            member.getName(),
            member.getNickname(),
            member.getPhoneNumber(),
            member.getLevel(),
            member.getProfileImage(),
            member.getSnsProvider().getValue(),
            member.getIdToken(),
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