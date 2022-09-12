package com.soumyadeep.myspringbootproject.service;

import com.soumyadeep.myspringbootproject.dto.AddMovieDto;
import com.soumyadeep.myspringbootproject.entity.*;
import com.soumyadeep.myspringbootproject.repository.GenreRepository;
import com.soumyadeep.myspringbootproject.repository.LanguageRepository;
import com.soumyadeep.myspringbootproject.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
            List<MovieLanguage> movieLanguages = data.getLanguages().stream().map(language -> convertToMovieLanguage(language)).collect(Collectors.toList());
            List<MovieGenre> movieGenres = data.getGenres().stream().map(genre -> convertToMovieGenre(genre)).collect(Collectors.toList());
            Movie movie = Movie.builder().
                    name(data.getName()).
                    releaseDate( LocalDate.of(year, month, day)).
                    movieGenres(movieGenres).
                    movieLanguages(movieLanguages).
                    build();
            movieRepository.save(movie);
        } catch (Exception e){
            System.out.println("error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private MovieLanguage convertToMovieLanguage(Long language){
        MovieLanguage movieLanguage = MovieLanguage.
                builder().
                languageId(language).
                build();
        return movieLanguage;
    }

    private MovieGenre convertToMovieGenre(Long genre){
        MovieGenre movieGenre = MovieGenre.
                builder().
                genreId(genre).
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

}
