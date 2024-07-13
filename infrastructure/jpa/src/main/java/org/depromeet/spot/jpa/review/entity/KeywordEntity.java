package org.depromeet.spot.jpa.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.Keyword;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "keywords")
@NoArgsConstructor
@AllArgsConstructor
public class KeywordEntity extends BaseEntity {

    @Column(name = "content", nullable = false, length = 50)
    private String content;

    public static KeywordEntity from(Keyword keyword) {
        return new KeywordEntity(keyword.getContent());
    }

    public Keyword toDomain() {
        return new Keyword(this.getId(), content);
    }
}
