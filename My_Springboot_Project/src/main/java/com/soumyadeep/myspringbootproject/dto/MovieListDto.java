package com.soumyadeep.myspringbootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieListDto {
    private String name;
    private String genre;
    private String language;
    private Float rating;
}
