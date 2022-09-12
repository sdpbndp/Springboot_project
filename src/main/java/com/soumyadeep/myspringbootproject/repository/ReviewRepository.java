package com.soumyadeep.myspringbootproject.repository;

import com.soumyadeep.myspringbootproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
