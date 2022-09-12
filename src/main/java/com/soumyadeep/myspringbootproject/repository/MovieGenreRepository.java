package com.soumyadeep.myspringbootproject.repository;

import com.soumyadeep.myspringbootproject.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {
}
