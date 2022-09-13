package com.soumyadeep.myspringbootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMovieDto {
    private Long movie;
    private Integer rating;
    private String review;
}
