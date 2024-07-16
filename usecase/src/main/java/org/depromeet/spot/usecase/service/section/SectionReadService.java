package org.depromeet.spot.usecase.service.section;

import java.util.List;

import org.depromeet.spot.common.exception.section.SectionException.SectionNotBelongStadiumException;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.out.section.SectionRepository;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class SectionReadService implements SectionReadUsecase {

    private final StadiumReadUsecase stadiumReadUsecase;
    private final SectionRepository sectionRepository;

    @Override
    public StadiumSections findAllByStadium(final Long stadiumId) {
        Stadium stadium = stadiumReadUsecase.findById(stadiumId);
        List<Section> sections = sectionRepository.findAllByStadium(stadiumId);
        List<SectionInfo> sectionInfos = sections.stream().map(SectionInfo::from).toList();
        return new StadiumSections(stadium.getSeatingChartImage(), sectionInfos);
    }

    @Override
    public void checkIsExistsInStadium(final Long stadiumId, final Long sectionId) {
        if (!existsInStadium(stadiumId, sectionId)) {
            throw new SectionNotBelongStadiumException();
        }
    }

    @Override
    public boolean existsInStadium(final Long stadiumId, final Long sectionId) {
        return sectionRepository.existsInStadium(stadiumId, sectionId);
    }
}
