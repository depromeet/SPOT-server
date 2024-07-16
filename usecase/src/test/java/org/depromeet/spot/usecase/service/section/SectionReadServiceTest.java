package org.depromeet.spot.usecase.service.section;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.domain.section.Section;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase.SectionInfo;
import org.depromeet.spot.usecase.port.in.section.SectionReadUsecase.StadiumSections;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase;
import org.depromeet.spot.usecase.service.fake.FakeSectionRepository;
import org.depromeet.spot.usecase.service.fake.FakeStadiumRepository;
import org.depromeet.spot.usecase.service.stadium.StadiumReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SectionReadServiceTest {

    private SectionReadService sectionReadService;
    private StadiumReadService stadiumReadService;
    private ReadStadiumHomeTeamUsecase readStadiumHomeTeamUsecase;

    @BeforeEach
    void init() {
        FakeStadiumRepository fakeStadiumRepository = new FakeStadiumRepository();
        FakeSectionRepository fakeSectionRepository = new FakeSectionRepository();

        this.stadiumReadService =
                StadiumReadService.builder()
                        .readStadiumHomeTeamUsecase(readStadiumHomeTeamUsecase)
                        .stadiumRepository(fakeStadiumRepository)
                        .build();

        this.sectionReadService =
                SectionReadService.builder()
                        .stadiumReadUsecase(stadiumReadService)
                        .sectionRepository(fakeSectionRepository)
                        .build();

        Stadium stadium =
                Stadium.builder()
                        .id(1L)
                        .name("잠실 야구 경기장")
                        .mainImage("mainImage1.png")
                        .seatingChartImage("seatingChartImage1.png")
                        .labeledSeatingChartImage("labeledSeatingChartImage1.png")
                        .isActive(true)
                        .build();
        fakeStadiumRepository.save(stadium);

        Section section1 =
                Section.builder()
                        .id(1L)
                        .stadiumId(1L)
                        .name("오렌지석")
                        .alias("응원석")
                        .labelRgbCode(new RgbCode(0, 0, 0))
                        .build();
        Section section2 =
                Section.builder()
                        .id(2L)
                        .stadiumId(1L)
                        .name("레드석")
                        .alias(null)
                        .labelRgbCode(new RgbCode(110, 100, 0))
                        .build();
        fakeSectionRepository.save(section1);
        fakeSectionRepository.save(section2);
    }

    @Test
    void findAllByStadium_는_주어진_경기장의_좌석배치도와_구역_정보를_반환한다() {
        // given
        final Long stadiumId = 1L;

        // when
        StadiumSections results = sectionReadService.findAllByStadium(stadiumId);

        // then
        assertEquals("seatingChartImage1.png", results.getSeatChart());

        List<SectionInfo> sectionList = results.getSectionList();
        assertThat(sectionList).hasSize(2);
        assertThat(sectionList)
                .anyMatch(
                        section -> section.getId().equals(1L) && section.getName().equals("오렌지석"));
        assertThat(sectionList)
                .anyMatch(section -> section.getId().equals(2L) && section.getName().equals("레드석"));
    }

    @Test
    void findAllByStadium_는_존재하지_않는_경기장_요청에_예외를_반환한다() {
        // given
        final Long stadiumId = -999L;

        // when
        // then
        assertThatThrownBy(() -> sectionReadService.findAllByStadium(stadiumId))
                .isInstanceOf(StadiumNotFoundException.class);
    }
}
