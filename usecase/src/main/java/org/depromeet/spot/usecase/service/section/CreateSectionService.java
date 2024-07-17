package org.depromeet.spot.usecase.service.section;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.depromeet.spot.common.exception.section.SectionException.SectionAliasDuplicateException;
import org.depromeet.spot.common.exception.section.SectionException.SectionNameDuplicateException;
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
        validate(commands);
        stadiumReadUsecase.checkIsExistsBy(stadiumId);

        List<Section> sections = commands.stream().map(c -> c.toDomain(stadiumId)).toList();
        sectionRepository.saveAll(sections);
    }

    private void validate(List<CreateSectionCommand> commands) {
        List<String> names = new ArrayList<>();
        List<String> aliases = new ArrayList<>();
        commands.forEach(
                command -> {
                    names.add(command.name());
                    aliases.add(command.alias());
                });

        checkIsDuplicateName(names);
        checkIsDuplicateAlias(aliases);
    }

    public void checkIsDuplicateName(List<String> names) {
        Set<String> namesSet = new HashSet<>(names);
        if (namesSet.size() < names.size()) {
            throw new SectionNameDuplicateException();
        }
    }

    public void checkIsDuplicateAlias(List<String> aliases) {
        Set<String> aliasSet = new HashSet<>(aliases);
        if (aliasSet.size() < aliases.size()) {
            throw new SectionAliasDuplicateException();
        }
    }
}
