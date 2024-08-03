package org.depromeet.spot.infrastructure.jpa.block.repository.row;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.depromeet.spot.domain.block.BlockRow;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlockRowJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void createRows(List<BlockRow> rows) {
        jdbcTemplate.batchUpdate(
                "insert into block_rows" + "(block_id, number, max_seats) values (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, rows.get(i).getBlock().getId());
                        ps.setInt(2, rows.get(i).getNumber());
                        ps.setInt(3, rows.get(i).getMaxSeats());
                    }

                    @Override
                    public int getBatchSize() {
                        return rows.size();
                    }
                });
    }
}
