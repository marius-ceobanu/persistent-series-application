package com.codecool.persistent_series.repository;

import com.codecool.persistent_series.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findByReleaseDateBetween(LocalDate from, LocalDate to);

    List<Episode> findByTitleContaining(String word);

    @Query("UPDATE Episode e SET e.releaseDate = :releaseDate WHERE e.season.id IN" +
            "(SELECT s.id FROM Season s WHERE s.name LIKE %:name%)")
    @Modifying(clearAutomatically = true)
    int updateAllEpisodesReleaseDateBySeasonReleaseDate(@Param("name") String name, LocalDate releaseDate);

}
