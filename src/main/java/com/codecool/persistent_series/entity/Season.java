package com.codecool.persistent_series.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Season {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int countEpisodes;
    private int releaseYear;

    @Singular
    @OneToMany(mappedBy = "season", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    private Set<Episode> episodes;

    @ManyToOne
    private Series series;

    public void countEpisodes() {
        countEpisodes = episodes.size();
    }
}
