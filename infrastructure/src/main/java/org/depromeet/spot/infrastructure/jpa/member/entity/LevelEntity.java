package org.depromeet.spot.infrastructure.jpa.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.member.Level;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "levels")
public class LevelEntity extends BaseEntity {

    @Column(name = "value", nullable = false, unique = true)
    private int value;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "mascot_image_url")
    private String mascotImageUrl;

    @Column(name = "level_up_image_url")
    private String levelUpImageUrl;

    public LevelEntity(Level level) {
        super(level.getId(), level.getCreatedAt(), level.getUpdatedAt(), level.getDeletedAt());
        value = level.getValue();
        title = level.getTitle();
        mascotImageUrl = level.getMascotImageUrl();
        levelUpImageUrl = level.getLevelUpImageUrl();
    }

    public static LevelEntity from(Level level) {
        return new LevelEntity(
                level.getValue(),
                level.getTitle(),
                level.getMascotImageUrl(),
                level.getLevelUpImageUrl());
    }

    public Level toDomain() {
        return new Level(
                this.getId(),
                value,
                title,
                mascotImageUrl,
                levelUpImageUrl,
                this.getCreatedAt(),
                this.getUpdatedAt(),
                this.getDeletedAt());
    }
}
