package org.depromeet.spot.usecase.service.stadium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.depromeet.spot.common.exception.stadium.StadiumException.StadiumNotFoundException;
import org.depromeet.spot.domain.block.Block;
import org.depromeet.spot.domain.hashtag.HashTag;
import org.depromeet.spot.domain.stadium.Stadium;
import org.depromeet.spot.domain.team.BaseballTeam;
import org.depromeet.spot.usecase.port.in.stadium.StadiumReadUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase;
import org.depromeet.spot.usecase.port.in.team.ReadStadiumHomeTeamUsecase.HomeTeamInfo;
import org.depromeet.spot.usecase.port.out.section.SectionRepository;
import org.depromeet.spot.usecase.port.out.stadium.StadiumRepository;
import org.depromeet.spot.usecase.service.block.ReadBlockTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumReadService implements StadiumReadUsecase {

    private final ReadStadiumHomeTeamUsecase readStadiumHomeTeamUsecase;
    private final ReadBlockTagService readBlockTagService;
    private final SectionRepository sectionRepository;
    private final StadiumRepository stadiumRepository;

    @Override
    public List<StadiumHomeTeamInfo> findAllStadiums() {
        Map<Stadium, List<BaseballTeam>> stadiumHomeTeams =
                readStadiumHomeTeamUsecase.findAllStadiumHomeTeam();
        List<StadiumHomeTeamInfo> immutableList =
                stadiumHomeTeams.entrySet().stream()
                        .map(
                                entry -> {
                                    Stadium stadium = entry.getKey();
                                    List<BaseballTeam> teams = entry.getValue();
                                    List<HomeTeamInfo> homeTeamInfos =
                                            teams.stream()
                                                    .map(
                                                            t ->
                                                                    new HomeTeamInfo(
                                                                            t.getId(),
                                                                            t.getAlias(),
                                                                            t.getLabelFontColor()
                                                                                    .getValue()))
                                                    .toList();

                                    return new StadiumHomeTeamInfo(
                                            stadium.getId(),
                                            stadium.getName(),
                                            homeTeamInfos,
                                            stadium.getMainImage(),
                                            stadium.isActive());
                                })
                        .toList();
        List<StadiumHomeTeamInfo> result = new ArrayList<>(immutableList);
        result.sort(
                Comparator.comparing((StadiumHomeTeamInfo info) -> !info.isActive())
                        .thenComparing(StadiumHomeTeamInfo::getName));
        return result;
    }

    @Override
    public List<StadiumNameInfo> findAllNames() {
        List<Stadium> stadiums = stadiumRepository.findAll();
        return stadiums.stream()
                .map(s -> new StadiumNameInfo(s.getId(), s.getName(), s.isActive()))
                .toList();
    }

    @Override
    public StadiumInfoWithSeatChart findWithSeatChartById(final Long id) {
        Stadium stadium = stadiumRepository.findById(id);
        List<StadiumSectionInfo> sections =
                sectionRepository.findAllByStadium(id).stream()
                        .map(StadiumSectionInfo::from)
                        .toList();
        List<HomeTeamInfo> homeTeams = readStadiumHomeTeamUsecase.findByStadium(id);
        List<StadiumBlockTagInfo> blockTags = makeBlockTagInfoByStadium(id);
        return StadiumInfoWithSeatChart.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .homeTeams(homeTeams)
                .thumbnail(stadium.getMainImage())
                .seatChartWithLabel(stadium.getLabeledSeatingChartImage())
                .sections(sections)
                .blockTags(blockTags)
                .build();
    }

    private List<StadiumBlockTagInfo> makeBlockTagInfoByStadium(final Long id) {
        List<StadiumBlockTagInfo> result = new ArrayList<>();
        Map<HashTag, List<Block>> blockTags = readBlockTagService.findAllByStadium(id);
        for (Entry<HashTag, List<Block>> blockTag : blockTags.entrySet()) {
            HashTag hashTag = blockTag.getKey();
            List<Block> blocks = blockTag.getValue();
            result.add(
                    StadiumBlockTagInfo.builder()
                            .id(hashTag.getId())
                            .name(hashTag.getName())
                            .description(hashTag.getDescription())
                            .blockCodes(blocks.stream().map(Block::getCode).toList())
                            .build());
        }
        return result;
    }

    @Override
    public Stadium findById(final Long id) {
        return stadiumRepository.findById(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return stadiumRepository.existsById(id);
    }

    @Override
    public void checkIsExistsBy(Long stadiumId) {
        if (!existsById(stadiumId)) {
            throw new StadiumNotFoundException();
        }
    }
}
