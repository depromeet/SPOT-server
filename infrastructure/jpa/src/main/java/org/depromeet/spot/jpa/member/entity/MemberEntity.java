package org.depromeet.spot.jpa.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.member.Member;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@NoArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public MemberEntity(
            Long userId,
            String email,
            String name,
            String nickname,
            String phoneNumber,
            Integer level,
            String profileImage,
            String snsProvider,
            String idToken,
            String myTeam,
            Integer role,
            LocalDateTime createdAt,
            LocalDateTime deletedAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.level = level;
        this.profileImage = profileImage;
        this.snsProvider = snsProvider;
        this.idToken = idToken;
        this.myTeam = myTeam;
        this.role = role;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static MemberEntity from(Member member) {
        return new MemberEntity(
                member.getUserId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPhoneNumber(),
                member.getLevel(),
                member.getProfileImage(),
                member.getSnsProvider(),
                member.getIdToken(),
                member.getMyTeam(),
                member.getRole(),
                member.getCreatedAt(),
                member.getDeletedAt());
    }

    public Member toDomain() {
        return new Member(
                userId,
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
                createdAt,
                deletedAt);
    }
}
