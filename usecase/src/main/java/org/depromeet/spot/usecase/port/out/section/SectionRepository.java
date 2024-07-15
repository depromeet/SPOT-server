package org.depromeet.spot.usecase.port.out.section;

import java.util.List;

import org.depromeet.spot.domain.section.Section;

public interface SectionRepository {

    List<Section> findAllByStadium(Long stadiumId);

    Section save(Section section);

    boolean existsInStadium(Long stadiumId, Long sectionId);
}
