package org.depromeet.spot.usecase.port.out.section;

import java.util.List;

import org.depromeet.spot.domain.section.Section;

public interface SectionRepository {

    List<Section> findAllByStadium(Long stadiumId);

    Section save(Section section);

    void saveAll(List<Section> sections);

    boolean existsInStadium(Long stadiumId, Long sectionId);

    Section findById(Long id);
}
