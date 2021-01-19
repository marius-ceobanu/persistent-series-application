package com.codecool.persistent_series.repository;

import com.codecool.persistent_series.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}
