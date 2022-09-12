package com.soumyadeep.myspringbootproject.service;

import com.soumyadeep.myspringbootproject.dto.AddMovieDto;
import com.soumyadeep.myspringbootproject.entity.Genre;
import com.soumyadeep.myspringbootproject.entity.Language;

import java.util.HashMap;
import java.util.List;

public interface MovieService {

    public HashMap<String, Object> validateAddMovieRequest(AddMovieDto data);

    public void addMovie(AddMovieDto data);

    public List<Language> gatAllLanguages();

    public List<Genre> gatAllGenres();

}
