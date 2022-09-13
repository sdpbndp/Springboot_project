package com.soumyadeep.myspringbootproject.controller;

import com.soumyadeep.myspringbootproject.dto.AddMovieDto;
import com.soumyadeep.myspringbootproject.dto.MovieDto;
import com.soumyadeep.myspringbootproject.dto.MovieListDto;
import com.soumyadeep.myspringbootproject.dto.ReviewMovieDto;
import com.soumyadeep.myspringbootproject.entity.Genre;
import com.soumyadeep.myspringbootproject.entity.Language;
import com.soumyadeep.myspringbootproject.entity.Movie;
import com.soumyadeep.myspringbootproject.service.MovieService;
import com.soumyadeep.myspringbootproject.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    UtilityService utilityService;

    @GetMapping("/add-movie")
    public String addMovie(){
        return "add-movie";
    }

    @PostMapping("/add-movie")
    public ResponseEntity addMovie(@RequestBody AddMovieDto data){
        HashMap<String, Object> result = movieService.validateAddMovieRequest(data);
        if(result.get("result").equals("0")){
            return utilityService.sendErrorResponse("Invalid Request", result.get("errors"), HttpStatus.BAD_REQUEST.value());
        }
        movieService.addMovie(data);
        return utilityService.sendSuccessResponse("Movie Added Successfully!!!",null);
    }

    @GetMapping("/all-genres")
    public ResponseEntity getAllGenres(){
        List<Genre> data = movieService.gatAllGenres();
        return utilityService.sendSuccessResponse(data);
    }

    @GetMapping("/all-languages")
    public ResponseEntity getAllLanguages(){
        List<Language> data = movieService.gatAllLanguages();
        return utilityService.sendSuccessResponse(data);
    }

    @GetMapping("/review-movie")
    public String reviewMovie(){
        return "review-movie";
    }

    @PostMapping("/review-movie")
    public ResponseEntity reviewMovie(@RequestBody ReviewMovieDto data){
        HashMap<String, Object> result = movieService.validateReviewMovieRequest(data);
        if(result.get("result").equals("0")){
            return utilityService.sendErrorResponse("Invalid Request", result.get("errors"), HttpStatus.BAD_REQUEST.value());
        }
        movieService.reviewMovie(data);
        return utilityService.sendSuccessResponse("Review Given Successfully!!!",null);
    }

    @GetMapping("/get-movies")
    public ResponseEntity getMovies(@RequestParam(required = false) Long genre, @RequestParam(required = false) Long language){
        if(genre == null && language == null){
            return utilityService.sendErrorResponse("Please provide at least any one of genre or language", null, HttpStatus.BAD_REQUEST.value());
        }
        List<MovieDto> data = movieService.getMovies(genre, language);
        return utilityService.sendSuccessResponse(data);
    }

    @GetMapping("/get-movie-list")
    public ResponseEntity getMovieLists(@RequestParam(required = false) Long genre, @RequestParam(required = false) Long language, @RequestParam(required = false) Integer year){
        List<MovieListDto> movies = movieService.getMovieList(genre, language, year);
        return utilityService.sendSuccessResponse(movies);
    }

    @GetMapping("/")
    public String getMovieLists(){
        return "movie-list";
    }

}
