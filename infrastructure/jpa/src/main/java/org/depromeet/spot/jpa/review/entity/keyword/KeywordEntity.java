package org.depromeet.spot.jpa.review.entity.keyword;

import jakarta.persistence.*;

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

    public static KeywordEntity from(org.depromeet.spot.domain.keyword.Keyword keyword) {
        return new KeywordEntity(keyword.getContent(), keyword.getIsPositive());
    }

    public org.depromeet.spot.domain.keyword.Keyword toDomain() {
        return org.depromeet.spot.domain.keyword.Keyword.builder()
                .id(this.getId())
                .content(content)
                .isPositive(isPositive)
                .build();
    }
}
