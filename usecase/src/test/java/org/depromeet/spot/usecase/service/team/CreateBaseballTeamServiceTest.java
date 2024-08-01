package org.depromeet.spot.usecase.service.team;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.depromeet.spot.common.exception.team.TeamException.DuplicateTeamNameException;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.service.fake.FakeBaseballTeamRepository;
import org.depromeet.spot.usecase.service.fake.FakeImageUploadPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateBaseballTeamServiceTest {

    private CreateBaseballTeamService createBaseballTeamService;

    @BeforeEach
    void init() {
        FakeImageUploadPort fakeImageUploadPort = new FakeImageUploadPort();
        FakeBaseballTeamRepository fakeBaseballTeamRepository = new FakeBaseballTeamRepository();
        this.createBaseballTeamService =
                CreateBaseballTeamService.builder()
                        .imageUploadPort(fakeImageUploadPort)
                        .baseballTeamRepository(fakeBaseballTeamRepository)
                        .build();

        BaseballTeam team =
                BaseballTeam.builder()
                        .id(1L)
                        .name("두산 베어스")
                        .alias("두산")
                        .logo("logo1.png")
                        .labelBackgroundColor("#FFFFFF")
                        .build();
        fakeBaseballTeamRepository.save(team);
    }

    @Test
    void 이미_존재하는_이름의_구단을_중복_저장할_수_없다() {
        // given
        String duplicateName = "두산 베어스";

        // when
        // then
        assertThatThrownBy(() -> createBaseballTeamService.checkExistsName(duplicateName))
                .isInstanceOf(DuplicateTeamNameException.class);
    }
}
