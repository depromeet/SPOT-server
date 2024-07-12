package org.depromeet.spot.jpa.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.depromeet.spot.domain.review.Keyword;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "keywords")
@NoArgsConstructor
public class KeywordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false, length = 50)
    private String content;

    public KeywordEntity(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static KeywordEntity from(Keyword keyword) {
        return new KeywordEntity(keyword.getId(), keyword.getContent());
    }

    public Keyword toDomain() {
        return new Keyword(id, content);
    }
}
