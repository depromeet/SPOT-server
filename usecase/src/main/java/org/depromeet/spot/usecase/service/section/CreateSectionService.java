package org.depromeet.spot.usecase.service.section;

import java.util.List;

import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.usecase.port.in.section.CreateSectionUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.section.SectionRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateSectionService implements CreateSectionUsecase {

    private final SectionRepository sectionRepository;
    private final StadiumReadUsecase stadiumReadUsecase;

    @Override
    public void createAll(final Long stadiumId, List<CreateSectionCommand> commands) {
        stadiumReadUsecase.checkIsExistsBy(stadiumId);
        List<Section> sections = commands.stream().map(c -> c.toDomain(stadiumId)).toList();
        sectionRepository.saveAll(sections);
    }
}
