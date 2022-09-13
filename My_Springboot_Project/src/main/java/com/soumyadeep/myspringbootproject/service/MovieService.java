package com.soumyadeep.myspringbootproject.service;

import com.soumyadeep.myspringbootproject.dto.AddMovieDto;
import com.soumyadeep.myspringbootproject.dto.MovieDto;
import com.soumyadeep.myspringbootproject.dto.ReviewMovieDto;
import com.soumyadeep.myspringbootproject.entity.Genre;
import com.soumyadeep.myspringbootproject.entity.Language;

import java.util.HashMap;
import java.util.List;

public interface MovieService {

    public HashMap<String, Object> validateAddMovieRequest(AddMovieDto data);

    public void addMovie(AddMovieDto data);

    public List<Language> gatAllLanguages();

    public List<Genre> gatAllGenres();

    public List<MovieDto> getMovies(Long genre, Long language);

    public HashMap<String, Object> validateReviewMovieRequest(ReviewMovieDto data);

    public void reviewMovie(ReviewMovieDto data);

}
