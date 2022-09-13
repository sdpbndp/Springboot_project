package com.soumyadeep.myspringbootproject.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "movie_id"
    )
    private List<MovieGenre> movieGenres;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "movie_id"
    )
    private List<MovieLanguage> movieLanguages;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "movie_id"
    )
    private List<Review> reviews;
}
