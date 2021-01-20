package com.codecool.persistent_series.repository;

import com.codecool.persistent_series.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findByReleaseDateBetween(LocalDate from, LocalDate to);

    List<Episode> findByTitleContaining(String word);

}
