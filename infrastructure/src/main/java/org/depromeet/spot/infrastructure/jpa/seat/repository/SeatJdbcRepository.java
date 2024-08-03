package org.depromeet.spot.infrastructure.jpa.seat.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.depromeet.spot.domain.seat.Seat;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void createSeats(List<Seat> seats) {
        jdbcTemplate.batchUpdate(
                "insert into seats"
                        + "(stadium_id, section_id, block_id, row_id, seat_number) values (?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, seats.get(i).getStadium().getId());
                        ps.setLong(2, seats.get(i).getSection().getId());
                        ps.setLong(3, seats.get(i).getBlock().getId());
                        ps.setLong(4, seats.get(i).getRow().getId());
                        ps.setInt(5, seats.get(i).getSeatNumber());
                    }

                    @Override
                    public int getBatchSize() {
                        return seats.size();
                    }
                });
    }
}
