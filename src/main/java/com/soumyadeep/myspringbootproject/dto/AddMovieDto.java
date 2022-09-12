package com.soumyadeep.myspringbootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddMovieDto {
    private String name;
    private String releaseDate;
    private List<Long> genres;
    private List<Long> languages;
}
