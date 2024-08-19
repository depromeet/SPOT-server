package org.depromeet.spot.infrastructure.jpa.section.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.depromeet.spot.domain.section.Section;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SectionJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void createSections(List<Section> sections) {
        jdbcTemplate.batchUpdate(
                "insert into sections" + "(stadium_id, name, alias) values (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, sections.get(i).getStadiumId());
                        ps.setString(2, sections.get(i).getName());
                        ps.setString(3, sections.get(i).getAlias());
                    }

                    @Override
                    public int getBatchSize() {
                        return sections.size();
                    }
                });
    }
}
