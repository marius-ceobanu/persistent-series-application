package com.codecool.persistent_series.repository;

import com.codecool.persistent_series.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    List<Series> findSeriesByDirector(String director);

    @Query(value = "SELECT e.title FROM Episode e WHERE e.season.id IN" +
                    "(SELECT s.id FROM Season s WHERE s.series.seriesId IN" +
                        "(SELECT p.seriesId FROM Series p WHERE p.name = :name))")
    List<String> findEpisodeTitlesBySeries(@Param("name") String name);

}
