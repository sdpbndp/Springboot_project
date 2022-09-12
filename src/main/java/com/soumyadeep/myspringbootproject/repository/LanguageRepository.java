package com.soumyadeep.myspringbootproject.repository;

import com.soumyadeep.myspringbootproject.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
