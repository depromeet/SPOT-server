package org.depromeet.spot.usecase.port.out.stadium;

import java.util.List;

import org.depromeet.spot.domain.stadium.Stadium;

public interface StadiumRepository {
    Stadium findById(Long id);

    List<Stadium> findAll();

    Stadium save(Stadium stadium);

    boolean existsById(Long id);
}
