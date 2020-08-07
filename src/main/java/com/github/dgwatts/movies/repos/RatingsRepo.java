package com.github.dgwatts.movies.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.dgwatts.movies.model.Rating;

@Repository
public interface RatingsRepo extends CrudRepository<Rating, Integer> {
}
