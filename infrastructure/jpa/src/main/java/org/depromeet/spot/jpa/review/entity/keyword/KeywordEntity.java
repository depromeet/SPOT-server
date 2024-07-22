package org.depromeet.spot.jpa.review.entity.keyword;

import jakarta.persistence.*;

import org.depromeet.spot.domain.review.keyword.Keyword;
import org.depromeet.spot.jpa.common.entity.BaseEntity;

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
        return Keyword.builder().id(this.getId()).content(content).isPositive(isPositive).build();
    }
}
