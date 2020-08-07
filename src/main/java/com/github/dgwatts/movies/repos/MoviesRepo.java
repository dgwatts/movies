package com.github.dgwatts.movies.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.dgwatts.movies.model.Movie;

@Repository
public interface MoviesRepo extends CrudRepository<Movie, Integer> {

	@Query("SELECT m from Movie m JOIN m.ratings r WHERE r.rating > ?1")
	List<Movie> findAllByRatingGreaterThan(int rating);

	List<Movie> findAllByDirectorId(int directorId);
}
