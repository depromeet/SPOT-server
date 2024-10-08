package org.depromeet.spot.usecase.port.in.section;

import java.util.List;

import org.depromeet.spot.domain.section.Section;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface SectionReadUsecase {

    List<Section> findAllBy(Long stadiumId);

    StadiumSections findAllByStadium(Long stadiumId);

    boolean existsInStadium(Long stadiumId, Long sectionId);

    void checkIsExistsInStadium(Long stadiumId, Long sectionId);

    Section findById(Long id);

    @Getter
    @AllArgsConstructor
    class StadiumSections {
        private final String seatChart;
        private final List<SectionInfo> sectionList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    class SectionInfo {
        private final Long id;
        private final String name;
        private final String alias;

        public static SectionInfo from(Section section) {
            return SectionInfo.builder()
                    .id(section.getId())
                    .name(section.getName())
                    .alias(section.getAlias())
                    .build();
        }
    }
}
