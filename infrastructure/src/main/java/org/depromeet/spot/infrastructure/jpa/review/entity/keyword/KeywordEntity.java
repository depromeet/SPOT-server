package org.depromeet.spot.infrastructure.jpa.review.entity.keyword;

import jakarta.persistence.*;

import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.infrastructure.jpa.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "keywords")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KeywordEntity extends BaseEntity {

    @Column(name = "content", nullable = false, length = 50, unique = true)
    private String content;

    @Column(name = "is_positive", nullable = false)
    private boolean isPositive;

    public static KeywordEntity from(Keyword keyword) {
        return new KeywordEntity(keyword.getContent(), keyword.getIsPositive());
    }

    public Keyword toDomain() {
        return new Keyword(this.getId(), content, isPositive);
    }

    public static KeywordEntity withKeyword(Keyword keyword) {
        return new KeywordEntity(keyword);
    }

    public KeywordEntity(Keyword keyword) {
        super(keyword.getId(), null, null, null);
        content = keyword.getContent();
        isPositive = keyword.getIsPositive();
    }
}
