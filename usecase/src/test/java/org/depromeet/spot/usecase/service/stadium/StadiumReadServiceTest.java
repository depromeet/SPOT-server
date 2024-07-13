package org.depromeet.spot.usecase.service.stadium;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.usecase.port.in.team.StadiumHomeTeamReadUsecase;
import org.depromeet.spot.usecase.service.fake.FakeStadiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StadiumReadServiceTest {

    private StadiumReadService stadiumReadService;
    private StadiumHomeTeamReadUsecase stadiumHomeTeamReadUsecase;

    @BeforeEach
    void init() {
        FakeStadiumRepository fakeStadiumRepository = new FakeStadiumRepository();

        this.stadiumReadService =
                StadiumReadService.builder()
                        .stadiumHomeTeamReadUsecase(stadiumHomeTeamReadUsecase)
                        .stadiumRepository(fakeStadiumRepository)
                        .build();

        Stadium stadium1 =
                Stadium.builder()
                        .id(1L)
                        .name("잠실 야구 경기장")
                        .mainImage("mainImage1.png")
                        .seatingChartImage("seatingChartImage1.png")
                        .labeledSeatingChartImage("labeledSeatingChartImage1.png")
                        .isActive(true)
                        .build();

        Stadium stadium2 =
                Stadium.builder()
                        .id(2L)
                        .name("부산 야구 경기장")
                        .mainImage("mainImage2.png")
                        .seatingChartImage("seatingChartImage2.png")
                        .labeledSeatingChartImage("labeledSeatingChartImage2.png")
                        .isActive(false)
                        .build();

        fakeStadiumRepository.save(stadium1);
        fakeStadiumRepository.save(stadium2);
    }

    @Test
    void findById는_요청_경기장을_반환한다() {
        // given
        final Long stadiumId = 1L;

        // when
        Stadium stadium = stadiumReadService.findById(stadiumId);

        // then
        assertAll(
                () -> assertEquals(1L, stadium.getId()),
                () -> assertEquals("잠실 야구 경기장", stadium.getName()),
                () -> assertEquals("mainImage1.png", stadium.getMainImage()),
                () -> assertEquals("seatingChartImage1.png", stadium.getSeatingChartImage()),
                () ->
                        assertEquals(
                                "labeledSeatingChartImage1.png",
                                stadium.getLabeledSeatingChartImage()),
                () -> assertTrue(stadium.isActive()));
    }

    @Test
    void findById는_존재하지_않는_경기장_요청에_예외를_반환한다() {
        // given
        final Long stadiumId = -999L;

        // when
        // then
        assertThatThrownBy(() -> stadiumReadService.findById(stadiumId))
                .isInstanceOf(StadiumNotFoundException.class);
    }
}
