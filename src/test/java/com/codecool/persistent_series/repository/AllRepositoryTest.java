package com.codecool.persistent_series.repository;

import com.codecool.persistent_series.entity.Episode;
import com.codecool.persistent_series.entity.Genre;
import com.codecool.persistent_series.entity.Season;
import com.codecool.persistent_series.entity.Series;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DataJpaTest
@ActiveProfiles("test")
class AllRepositoryTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private TestEntityManager entityManager;

    public void populateLight() {
        Episode S1E1 = Episode.builder()
                                .title("Wartime")
                                .length(59)
                                .releaseDate(LocalDate.of(2019, 3, 27))
                                .build();
        Episode S1E2 = Episode.builder()
                                .title("Civil Union")
                                .length(57)
                                .releaseDate(LocalDate.of(2019, 3, 27))
                                .build();
        Episode S2E1 = Episode.builder()
                                .title("Boss Fight")
                                .length(60)
                                .releaseDate(LocalDate.of(2020, 3, 27))
                                .build();
        Episode S2E2 = Episode.builder()
                                .title("BFF")
                                .length(60)
                                .releaseDate(LocalDate.of(2020, 3, 27))
                                .build();
        Season S1 = Season.builder()
                                .name("Season 1")
                                .releaseYear(2019)
                                .episodes(Sets.newLinkedHashSet(S1E1, S1E2))
                                .build();
        Season S2 = Season.builder()
                                .name("Season 2")
                                .releaseYear(2020)
                                .episodes(Sets.newLinkedHashSet(S2E1, S2E2))
                                .build();
        Series ozark = Series.builder()
                                .name("Ozark")
                                .genre(Genre.CRIME)
                                .releaseDate(LocalDate.of(2017, 7, 21))
                                .director("Bill Dubuque")
                                .seasons(Sets.newLinkedHashSet(S1, S2))
                                .build();
        seriesRepository.save(ozark);
    }

    public void populateHeavy() {
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
    }

    @Test
    public void saveOneSimpleSeries() {
        Series ozark = Series.builder()
                                .name("Ozark")
                                .genre(Genre.CRIME)
                                .releaseDate(LocalDate.of(2017, 7, 21))
                                .director("Bill Dubuque")
                                .build();
        seriesRepository.save(ozark);

        Assertions.assertEquals(1, seriesRepository.findAll().size());
    }

    @Test
    public void saveMultipleSimpleSeries() {
        Series ozark = Series.builder()
                                .name("Ozark")
                                .genre(Genre.CRIME)
                                .releaseDate(LocalDate.of(2017, 7, 21))
                                .director("Bill Dubuque")
                                .build();
        Series ozark2 = Series.builder()
                                .name("Ozark2")
                                .genre(Genre.COMEDY)
                                .releaseDate(LocalDate.of(2020, 7, 21))
                                .director("Bill Dubuque")
                                .build();
        Series ozark3 = Series.builder()
                                .name("Ozark2")
                                .genre(Genre.THRILLER)
                                .releaseDate(LocalDate.of(2019, 10, 21))
                                .director("Bill Dubuque")
                                .build();
        seriesRepository.saveAll(Lists.newArrayList(ozark, ozark2, ozark3));

        Assertions.assertEquals(3, seriesRepository.findAll().size());
    }

    @Test
    public void transientFieldNotSaved() {
        Series ozark = Series.builder()
                                .name("Ozark")
                                .genre(Genre.COMEDY)
                                .releaseDate(LocalDate.of(2020, 7, 21))
                                .director("Bill Dubuque")
                                .producer("HBO")
                                .build();
        seriesRepository.save(ozark);
        entityManager.clear();

        List<Series> allSeries = seriesRepository.findAll();
        Assertions.assertTrue(allSeries.stream().allMatch(series -> series.getProducer().equals("")));
    }

    @Test
    public void nameShouldNotBeNullForSeries() {
        Series ozark = Series.builder()
                                .genre(Genre.COMEDY)
                                .releaseDate(LocalDate.of(2020, 7, 21))
                                .director("Bill Dubuque")
                                .build();
        Assertions.assertThrows(DataIntegrityViolationException.class,
        () -> seriesRepository.save(ozark));
    }

    @Test
    public void pyramidPersistence() {
        populateLight();

        List<Episode> allEpisodes = episodeRepository.findAll();
        Assertions.assertEquals(4, allEpisodes.size());
        Assertions.assertTrue(allEpisodes.stream().allMatch(episode -> episode.getId() > 0L));

        List<Season> allSeasons = seasonRepository.findAll();
        Assertions.assertEquals(2, allSeasons.size());
        Assertions.assertTrue(allSeasons.stream().allMatch(season -> season.getId() > 0L));
    }

    @Test
    public void seriesPyramidIsPersistedAndDeletedWithTheSeries () {
        populateLight();

        List<Episode> allEpisodes = episodeRepository.findAll();
        Assertions.assertEquals(4, allEpisodes.size());
        Assertions.assertTrue(allEpisodes.stream().allMatch(episode -> episode.getId() > 0L));

        List<Season> allSeasons = seasonRepository.findAll();
        Assertions.assertEquals(2, allSeasons.size());
        Assertions.assertTrue(allSeasons.stream().allMatch(season -> season.getId() > 0L));

        seriesRepository.deleteAll();

        Assertions.assertEquals(0, seriesRepository.findAll().size());
        Assertions.assertEquals(0, seasonRepository.findAll().size());
        Assertions.assertEquals(0, episodeRepository.findAll().size());
    }

    @Test
    public void findByReleaseDateBetween() {
        populateHeavy();

        List<Episode> result = episodeRepository.findByReleaseDateBetween(
                                    LocalDate.of(2016, 1, 1),
                                    LocalDate.of(2018, 1, 1)
        );

        Assertions.assertEquals(10, result.size());
    }

    @Test
    public void findByTitleContaining() {
        populateHeavy();

        List<Episode> result = episodeRepository.findByTitleContaining("The");

        Assertions.assertEquals(5, result.size());
    }

    @Test
    public void updateAllEpisodesReleaseDateBySeasonReleaseDate() {
        populateHeavy();

        List<Episode> result = episodeRepository.findByReleaseDateBetween(
                                        LocalDate.of(2017, 1, 1),
                                        LocalDate.of(2018, 1, 1)
        );

        Assertions.assertEquals(10, result.size());

        int updatedEpisodes = episodeRepository.updateAllEpisodesReleaseDateBySeasonReleaseDate("2",
                                        LocalDate.of(2017, 7, 21));

        result = episodeRepository.findByReleaseDateBetween(
                        LocalDate.of(2017, 1, 1),
                        LocalDate.of(2018, 1, 1)
        );

        Assertions.assertEquals(10, updatedEpisodes);
        Assertions.assertEquals(20, result.size());

    }
}