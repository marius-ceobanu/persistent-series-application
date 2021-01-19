package com.codecool.persistent_series.repository;

import com.codecool.persistent_series.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
