package org.depromeet.spot.infrastructure.jpa.hashtag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.hashtag.HashTag;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hashtags")
public class HashTagEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    public static HashTagEntity from(HashTag hashTag) {
        HashTagEntity entity = new HashTagEntity();
        entity.name = hashTag.getName();
        entity.description = hashTag.getDescription();
        if (hashTag.getId() != null) entity.setId(hashTag.getId());
        return entity;
    }

    public HashTag toDomain() {
        return new HashTag(this.getId(), name, description);
    }
}
