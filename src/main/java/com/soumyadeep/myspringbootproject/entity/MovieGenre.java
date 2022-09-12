package com.soumyadeep.myspringbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "movie_genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "genre_id")
    private Long genreId;
}
