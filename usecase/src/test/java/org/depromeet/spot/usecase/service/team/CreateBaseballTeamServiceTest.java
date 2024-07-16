package org.depromeet.spot.usecase.service.team;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.depromeet.spot.common.exception.team.TeamException.DuplicateTeamNameException;
import org.depromeet.spot.domain.common.RgbCode;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.service.fake.FakeBaseballTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateBaseballTeamServiceTest {

    private CreateBaseballTeamService createBaseballTeamService;

    @BeforeEach
    void init() {
        FakeBaseballTeamRepository fakeBaseballTeamRepository = new FakeBaseballTeamRepository();
        this.createBaseballTeamService =
                CreateBaseballTeamService.builder()
                        .baseballTeamRepository(fakeBaseballTeamRepository)
                        .build();

        BaseballTeam team =
                BaseballTeam.builder()
                        .id(1L)
                        .name("두산 베어스")
                        .alias("두산")
                        .logo("logo1.png")
                        .labelRgbCode(new RgbCode(0, 0, 0))
                        .build();
        fakeBaseballTeamRepository.save(team);
    }

    @Test
    void 이미_존재하는_이름의_구단을_중복_저장할_수_없다() {
        // given
        BaseballTeam team =
                BaseballTeam.builder()
                        .id(1L)
                        .name("두산 베어스")
                        .alias("두산")
                        .logo("logo1.png")
                        .labelRgbCode(new RgbCode(0, 0, 0))
                        .build();
        List<BaseballTeam> teams = List.of(team);

        // when
        // then
        assertThatThrownBy(() -> createBaseballTeamService.saveAll(teams))
                .isInstanceOf(DuplicateTeamNameException.class);
    }
}
