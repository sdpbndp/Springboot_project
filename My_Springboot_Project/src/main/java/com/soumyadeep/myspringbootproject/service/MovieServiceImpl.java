package com.soumyadeep.myspringbootproject.service;

import com.soumyadeep.myspringbootproject.dto.AddMovieDto;
import com.soumyadeep.myspringbootproject.dto.MovieDto;
import com.soumyadeep.myspringbootproject.dto.MovieListDto;
import com.soumyadeep.myspringbootproject.dto.ReviewMovieDto;
import com.soumyadeep.myspringbootproject.entity.*;
import com.soumyadeep.myspringbootproject.repository.GenreRepository;
import com.soumyadeep.myspringbootproject.repository.LanguageRepository;
import com.soumyadeep.myspringbootproject.repository.MovieRepository;
import com.soumyadeep.myspringbootproject.repository.ReviewRepository;
import com.soumyadeep.myspringbootproject.repository.MovieGenreRepository;
import com.soumyadeep.myspringbootproject.repository.MovieLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private MovieLanguageRepository movieLanguageRepository;

    @Override
    public HashMap<String, Object> validateAddMovieRequest(AddMovieDto data) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, String> errors = new HashMap<>();
            if(data == null){
                errors.put("name", "Name is required");
                errors.put("releaseDate", "ReleaseDate is required");
                errors.put("genres", "Genres is required");
                errors.put("languages", "Languages is required");
            }
            if(data.getName() == null || data.getName().equals("")){
                errors.put("name", "Name is required");
            } else if(data.getName().length() > 256) {
                errors.put("name", "Movie name cannot contains more than 256 characters");
            } else {
                Optional<Movie> optionalMovie = movieRepository.findByNameIgnoreCase(data.getName());
                if(optionalMovie.isPresent()){
                    errors.put("name", "Movie name is already exist");
                }
            }
            if(data.getReleaseDate() == null || data.getReleaseDate().equals("")){
                errors.put("releaseDate", "ReleaseDate is required");
            } else {
                String date[] = data.getReleaseDate().split("[\\/-]");
                int day = Integer.parseInt(date[2]);
                int month = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[0]);
                LocalDate today = LocalDate.now();
                if(!today.isAfter(LocalDate.of(year, month, day)) && !today.equals(LocalDate.of(year, month, day))){
                    errors.put("releaseDate", "ReleaseDate should not be future date");
                }
            }

            if(data.getGenres() == null || data.getGenres().size() == 0){
                errors.put("genres", "Genres is required");
            }
            if(data.getLanguages() == null || data.getLanguages().size() == 0){
                errors.put("languages", "Languages is required");
            }
            result.put("result", errors.size() == 0 ? "1" : "0");
            result.put("errors", errors);
            return result;
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    @Transactional()
    public void addMovie(AddMovieDto data) {
        try {
            String date[] = data.getReleaseDate().split("[\\/-]");
            int day = Integer.parseInt(date[2]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[0]);
            Movie movie = Movie.builder().
                    name(data.getName()).
                    releaseDate( LocalDate.of(year, month, day)).
                    build();
            Movie savedMovie = movieRepository.save(movie);
            List<MovieLanguage> movieLanguages = data.getLanguages().stream().map(language -> convertToMovieLanguage(language, savedMovie.getId())).collect(Collectors.toList());
            List<MovieGenre> movieGenres = data.getGenres().stream().map(genre -> convertToMovieGenre(genre, savedMovie.getId())).collect(Collectors.toList());
            movieGenreRepository.saveAll(movieGenres);
            movieLanguageRepository.saveAll(movieLanguages);
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private MovieLanguage convertToMovieLanguage(Long language, Long movieId){
        MovieLanguage movieLanguage = MovieLanguage.
                builder().
                languageId(language).
                movieId(movieId).
                build();
        return movieLanguage;
    }

    private MovieGenre convertToMovieGenre(Long genre, Long movieId){
        MovieGenre movieGenre = MovieGenre.
                builder().
                genreId(genre).
                movieId(movieId).
                build();
        return movieGenre;
    }

    @Override
    public List<Language> gatAllLanguages() {
        try {
            return languageRepository.findAll();
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    public List<Genre> gatAllGenres() {
        try {
            return genreRepository.findAll();
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    public List<MovieDto> getMovies(Long genre, Long language) {
        try {
            List<Movie> movies;
            if(genre != null && language != null)
                movies = movieRepository.findDistinctByMovieGenres_GenreIdAndMovieLanguages_LanguageId(genre, language);
            else if(genre == null)
                movies = movieRepository.findDistinctByMovieLanguages_LanguageId(language);
            else
                movies = movieRepository.findDistinctByMovieGenres_GenreId(genre);
            return movies.stream().map(movie -> convertMoviesToMovieDto(movie)).collect(Collectors.toList());
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    public HashMap<String, Object> validateReviewMovieRequest(ReviewMovieDto data) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            HashMap<String, String> errors = new HashMap<>();
            if(data == null){
                errors.put("review", "Review is required");
                errors.put("rating", "Rating is required");
                errors.put("movie", "Movie is required");
            }
            if(data.getReview() == null || data.getReview().equals("")){
                errors.put("review", "Review is required");
            } else if(data.getReview().length() > 1000) {
                errors.put("review", "Review cannot contains more than 1000 characters");
            }
            if(data.getRating() == null){
                errors.put("rating", "Rating is required");
            } else if (data.getRating() > 5 || data.getRating() < 0){
                errors.put("rating", "Rating should be 0-5");
            }
            if(data.getMovie() == null){
                errors.put("movie", "Movie is required");
            }
            result.put("result", errors.size() == 0 ? "1" : "0");
            result.put("errors", errors);
            return result;
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    public void reviewMovie(ReviewMovieDto data) {
        try {
            Review review = Review.builder().
                    movieId(data.getMovie()).
                    review(data.getReview()).
                    rating(data.getRating()).
                    build();
            reviewRepository.save(review);
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    public List<MovieListDto> getMovieList(Long genre, Long language, Integer year) {
        try {
            List<Movie> movies;
//            if(genre != null && language != null)
//                movies = movieRepository.findDistinctByMovieGenres_GenreIdAndMovieLanguages_LanguageId(genre, language);
//            else if(genre == null)
//                movies = movieRepository.findDistinctByMovieLanguages_LanguageId(language);
//            else
//                movies = movieRepository.findDistinctByMovieGenres_GenreId(genre);
            movies = movieRepository.findAll();
            movies.stream().forEach(data -> System.out.println(data.toString()));
//            System.out.println();
            return movies.stream().map(data->convertMoviesToMovieListDto(data)).collect(Collectors.toList());
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private MovieDto convertMoviesToMovieDto(Movie movie){
        return MovieDto.builder().
                id(movie.getId()).
                name(movie.getName()).
                build();
    }

    private MovieListDto convertMoviesToMovieListDto(Movie movie){
        Float rating = 0F;
        Integer i = 0;
        for(i = 0; i<movie.getReviews().size(); i++){
            rating = rating.floatValue() + movie.getReviews().get(i).getRating();
        }
        return MovieListDto.builder().
                name(movie.getName()).
//                genre(movie.getMovieGenres().stream().map(data -> data.getGenre().getName()).collect(Collectors.toList()).toString()).
                rating(rating/movie.getReviews().size()).
//                language(movie.getMovieLanguages().stream().map(data -> data.getLanguage().getName()).collect(Collectors.toList()).toString()).
                build();
    }

}
