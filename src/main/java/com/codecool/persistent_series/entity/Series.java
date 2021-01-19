package com.codecool.persistent_series.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Series {

    @Id
    @GeneratedValue
    private Long seriesId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private int countSeasons;

    @Singular
    @OneToMany(mappedBy = "series",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    private Set<Season> seasons;

    private LocalDate releaseDate;
    private String director;

    @ElementCollection
    @Singular("actor")
    private List<String> cast;

    @Transient
    private String producer;

    public void countSeasons() {
        countSeasons = seasons.size();
    }
}
