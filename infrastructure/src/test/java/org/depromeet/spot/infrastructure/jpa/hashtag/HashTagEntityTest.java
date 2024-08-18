package org.depromeet.spot.infrastructure.jpa.hashtag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.depromeet.spot.domain.hashtag.HashTag;
import org.junit.jupiter.api.Test;

class HashTagEntityTest {

    @Test
    void 도메인을_entity로_매핑할_때_ID가_있는_도메인으로_entity를_생성할_수_있다() {
        // given
        final long id = 1L;
        final String name = "해시태그";
        HashTag hashTag = new HashTag(id, name);

        // when
        HashTagEntity entity = HashTagEntity.from(hashTag);

        // then
        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
    }

    @Test
    void 신규_해시태그를_생성할_때_ID가_없는_도메인으로_entity를_생성할_수_있다() {
        // given
        final Long id = null;
        final String name = "해시태그";
        HashTag hashTag = new HashTag(id, name);

        // when
        HashTagEntity entity = HashTagEntity.from(hashTag);

        // then
        assertNull(entity.getId());
        assertEquals(name, entity.getName());
    }
}
