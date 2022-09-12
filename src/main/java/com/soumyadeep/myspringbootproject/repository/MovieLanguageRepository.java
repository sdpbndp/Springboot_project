package com.soumyadeep.myspringbootproject.repository;

import com.soumyadeep.myspringbootproject.entity.MovieLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieLanguageRepository extends JpaRepository<MovieLanguage, Long> {
}
