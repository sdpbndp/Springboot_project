package com.soumyadeep.myspringbootproject.repository;

import com.soumyadeep.myspringbootproject.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
