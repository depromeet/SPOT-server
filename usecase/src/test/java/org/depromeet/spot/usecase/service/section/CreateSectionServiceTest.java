package org.depromeet.spot.usecase.service.section;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.depromeet.spot.common.exception.section.SectionException.SectionAliasDuplicateException;
import org.depromeet.spot.common.exception.section.SectionException.SectionNameDuplicateException;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.service.fake.FakeSectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateSectionServiceTest {

    private CreateSectionService createSectionService;
    private StadiumReadUsecase stadiumReadUsecase;

    @BeforeEach
    void init() {
        FakeSectionRepository fakeSectionRepository = new FakeSectionRepository();
        this.createSectionService =
                CreateSectionService.builder()
                        .sectionRepository(fakeSectionRepository)
                        .stadiumReadUsecase(stadiumReadUsecase)
                        .build();
    }

    @Test
    public void 요청에_이름과_별칭에_중복이_없다면_유효성_검증을_통과한다() {
        // given
        List<String> names = List.of("Section1", "Section2", "Section3");
        List<String> aliases = Arrays.asList("Alias1", "Alias2", null);

        // when
        // then
        assertDoesNotThrow(() -> createSectionService.checkIsDuplicateName(names));
        assertDoesNotThrow(() -> createSectionService.checkIsDuplicateAlias(aliases));
    }

    @Test
    void 요청에_중복된_이름이_있다면_에러를_반환한다() {
        // given
        List<String> names = List.of("Section1", "Section1", "Section2");

        // when
        // then
        assertThrows(
                SectionNameDuplicateException.class,
                () -> createSectionService.checkIsDuplicateName(names));
    }

    @Test
    void 요청에_중복된_별칭이_있다면_에러를_반환한다() {
        // given
        List<String> aliases = Arrays.asList(null, "Alias1", "Alias1");

        // when
        // then
        assertThrows(
                SectionAliasDuplicateException.class,
                () -> createSectionService.checkIsDuplicateAlias(aliases));
    }
}
