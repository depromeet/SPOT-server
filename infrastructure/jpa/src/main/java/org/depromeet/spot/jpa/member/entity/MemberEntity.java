package org.depromeet.spot.jpa.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.MemberRole;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "level_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private LevelEntity level;

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

    public static MemberEntity of(Member member, Level level) {
        return new MemberEntity(
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPhoneNumber(),
                new LevelEntity(level),
                member.getProfileImage(),
                member.getSnsProvider().getValue(),
                member.getIdToken(),
                member.getTeamId(),
                member.getRole().getValue());
    }

    public static MemberEntity withMember(Member member) {
        return new MemberEntity(member);
    }

    public MemberEntity(Member member) {
        super(member.getId(), null, null, null);
        email = member.getEmail();
        name = member.getName();
        nickname = member.getNickname();
        phoneNumber = member.getPhoneNumber();
        level = new LevelEntity(member.getLevel());
        profileImage = member.getProfileImage();
        snsProvider = member.getSnsProvider().getValue();
        idToken = member.getIdToken();
        teamId = member.getTeamId();
        role = member.getRole().getValue();
    }

    public Member toDomain() {
        return new Member(
                this.getId(),
                email,
                name,
                nickname,
                phoneNumber,
                level.toDomain(),
                profileImage,
                SnsProvider.valueOf(snsProvider),
                idToken,
                teamId,
                MemberRole.valueOf(role),
                this.getCreatedAt(),
                this.getDeletedAt(),
                this.getUpdatedAt());
    }
}
