package com.codecool.persistent_series;

import com.codecool.persistent_series.entity.Episode;
import com.codecool.persistent_series.entity.Genre;
import com.codecool.persistent_series.entity.Season;
import com.codecool.persistent_series.entity.Series;
import com.codecool.persistent_series.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class PersistentSeriesApplication {

    @Autowired
    private SeriesRepository seriesRepository;

    public static void main(String[] args) {
        SpringApplication.run(PersistentSeriesApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {

            List<String> s1Titles = List.of("Sugarwood", "Blue Cat", "My Dripping Sleep",
            "Tonight We Improvise", "Ruling Days", "Book of Ruth",
            "Nest Box", "Kaleidoscope", "Coffee, Black", "The Toll");
            List<String> s2Titles = List.of("Reparations", "The Precious Blood of Jesus",
            "Once a Langmore...", "Stag", "Game Day", "Outer Darkness",
            "One Way Out", "The Big Sleep", "The Badger", "The Gold Coast");
            List<String> s3Titles = List.of("Wartime", "Civil Union", "Kevin Cronin Was Here",
            "Boss Fight", "It Came from Michoacan", "Su Casa Es Mi Casa",
            "In Case of Emergency", "BFF", "Fire Pink", "All In");

            Season S1 = Season.builder()
                .name("Season 1")
                .releaseYear(2017)
                .episodes(
                IntStream.range(0, 10).boxed().map(i -> Episode.builder()
                                                    .title(s1Titles.get(i))
                                                    .releaseDate(LocalDate.of(2017, 7, 21))
                                                    .length(50)
                                                    .build()).collect(Collectors.toSet())
                )
                .build();
            S1.countEpisodes();

            Season S2 = Season.builder()
                .name("Season 2")
                .releaseYear(2018)
                .episodes(
                IntStream.range(0, 10).boxed().map(i -> Episode.builder()
                                                    .title(s2Titles.get(i))
                                                    .releaseDate(LocalDate.of(2018, 5, 31))
                                                    .length(50)
                                                    .build()).collect(Collectors.toSet())
                )
                .build();
            S2.countEpisodes();

            Season S3 = Season.builder()
                .name("Season 3")
                .releaseYear(2020)
                .episodes(
                IntStream.range(0, 10).boxed().map(i -> Episode.builder()
                                                    .title(s3Titles.get(i))
                                                    .releaseDate(LocalDate.of(2020, 3, 27))
                                                    .length(50)
                                                    .build()).collect(Collectors.toSet())
                )
                .build();
            S3.countEpisodes();

            Series ozark = Series.builder()
                                    .name("Ozark")
                                    .genre(Genre.CRIME)
                                    .releaseDate(LocalDate.of(2017, 7, 21))
                                    .director("Bill Dubuque")
                                    .actor("Jason Bateman")
                                    .actor("Laura Linney")
                                    .actor("Sofia Hublitz")
                                    .season(S1)
                                    .season(S2)
                                    .season(S3)
                                    .build();
            ozark.countSeasons();

            S1.getEpisodes().forEach(episode -> episode.setSeason(S1));
            S1.setSeries(ozark);
            S2.getEpisodes().forEach(episode -> episode.setSeason(S2));
            S2.setSeries(ozark);
            S3.getEpisodes().forEach(episode -> episode.setSeason(S3));
            S3.setSeries(ozark);

            seriesRepository.save(ozark);

        };
    }
}
