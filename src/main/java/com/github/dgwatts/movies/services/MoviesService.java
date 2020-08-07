package com.github.dgwatts.movies.services;

import java.util.List;

import com.github.dgwatts.movies.model.Movie;
import com.github.dgwatts.movies.services.impl.CrudService;

public interface MoviesService extends CrudService<Movie> {
	List<Movie> findAllByRatingGreaterThan(int rating);

	List<Movie> findAllByDirectorId(int directorId);
}
