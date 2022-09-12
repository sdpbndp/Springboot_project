package com.soumyadeep.myspringbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "movie_language")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "language_id")
    private Long languageId;
}
