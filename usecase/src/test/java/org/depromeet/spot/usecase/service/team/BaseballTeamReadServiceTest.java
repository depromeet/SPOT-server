package org.depromeet.spot.usecase.service.team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.service.fake.FakeBaseballTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseballTeamReadServiceTest {

    private BaseballTeamReadService baseballTeamReadService;

    @BeforeEach
    void init() {
        FakeBaseballTeamRepository fakeBaseballTeamRepository = new FakeBaseballTeamRepository();
        this.baseballTeamReadService =
                BaseballTeamReadService.builder()
                        .baseballTeamRepository(fakeBaseballTeamRepository)
                        .build();

        BaseballTeam team1 =
                BaseballTeam.builder()
                        .id(1L)
                        .name("두산 베어스")
                        .alias("두산")
                        .logo("logo1.png")
                        .labelRgbCode(new RgbCode(0, 0, 0))
                        .build();
        BaseballTeam team2 =
                BaseballTeam.builder()
                        .id(2L)
                        .name("SSG 랜더스")
                        .alias("SSG")
                        .logo("logo2.png")
                        .labelRgbCode(new RgbCode(100, 0, 0))
                        .build();

        fakeBaseballTeamRepository.save(team1);
        fakeBaseballTeamRepository.save(team2);
    }

    @Test
    void findAll_은_모든_구단을_반환한다() {
        // given
        // when
        List<BaseballTeam> teams = baseballTeamReadService.findAll();

        // then
        assertAll(
                () -> assertThat(teams).hasSize(2),
                () ->
                        assertThat(teams)
                                .anyMatch(
                                        team ->
                                                team.getId().equals(1L)
                                                        && team.getName().equals("두산 베어스")),
                () ->
                        assertThat(teams)
                                .anyMatch(
                                        team ->
                                                team.getId().equals(2L)
                                                        && team.getName().equals("SSG 랜더스")));
    }
}
