package com.codecool.persistent_series.repository;

import com.codecool.persistent_series.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
