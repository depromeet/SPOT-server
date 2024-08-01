package org.depromeet.spot.jpa.team.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.depromeet.spot.domain.team.BaseballTeam;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BaseballTeamJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void createBaseballTeams(List<BaseballTeam> teams) {
        jdbcTemplate.batchUpdate(
                "insert into baseball_teams"
                        + "(name, alias, logo, label_background_color) values (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, teams.get(i).getName());
                        ps.setString(2, teams.get(i).getAlias());
                        ps.setString(3, teams.get(i).getLogo());
                        ps.setString(4, teams.get(i).getLabelBackgroundColor());
                    }

                    @Override
                    public int getBatchSize() {
                        return teams.size();
                    }
                });
    }
}
