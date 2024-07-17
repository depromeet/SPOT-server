package org.depromeet.spot.usecase.port.in.section;

import java.util.List;

import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.domain.section.Section;

import lombok.Builder;

public interface CreateSectionUsecase {

    void createAll(Long stadiumId, List<CreateSectionCommand> commands);

    @Builder
    record CreateSectionCommand(String name, String alias, RgbCode labelRgbCode) {

        public Section toDomain(final Long stadiumId) {
            return Section.builder()
                    .stadiumId(stadiumId)
                    .name(name)
                    .alias(alias)
                    .labelRgbCode(labelRgbCode)
                    .build();
        }
    }
}