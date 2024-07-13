package org.depromeet.spot.jpa.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true, length = 10)
    private String nickname;

    @Column(name = "phone_number", nullable = false, unique = true, length = 13)
    private String phoneNumber;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "sns_provider", nullable = false, length = 20)
    private String snsProvider;

    @Column(name = "id_token", nullable = false, unique = true, length = 255)
    private String idToken;

    @Column(name = "my_team", nullable = false, length = 10)
    private String myTeam;

    @Column(name = "role", nullable = false)
    private Integer role;

    public static MemberEntity from(Member member) {
        return new MemberEntity(
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPhoneNumber(),
                member.getLevel(),
                member.getProfileImage(),
                member.getSnsProvider(),
                member.getIdToken(),
                member.getMyTeam(),
                member.getRole());
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
                snsProvider,
                idToken,
                myTeam,
                role,
                this.getCreatedAt(),
                this.getDeletedAt());
    }
}
