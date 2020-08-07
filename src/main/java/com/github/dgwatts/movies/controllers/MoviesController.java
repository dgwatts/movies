package com.github.dgwatts.movies.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.dgwatts.movies.model.Director;
import com.github.dgwatts.movies.model.Movie;
import com.github.dgwatts.movies.services.MoviesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="movies",  description="Operations for Movies")
public class MoviesController {

	private final MoviesService moviesService;

	public MoviesController(@Autowired MoviesService moviesService) {
		this.moviesService = moviesService;
	}

	@PostMapping(value = "/movies", consumes = "application/json")
	@ApiOperation(value = "Create a new Movie", response = Movie.class)
	public ResponseEntity<Movie> create(@RequestBody Movie movie) {
		return new ResponseEntity<>(moviesService.create(movie), HttpStatus.OK);
	}

	@GetMapping(value = "/movies/{id}")
	@ApiOperation(value = "Retrieve a Movie", response = Movie.class)
	public ResponseEntity<Movie> getOne(@PathVariable int id) {
		return new ResponseEntity<>(moviesService.getOne(id), HttpStatus.OK);
	}

	@GetMapping(value = "/movies")
	@ApiOperation(value = "Retrieve all movies", response = List.class)
	public ResponseEntity<List<Movie>> getAll() {
		return new ResponseEntity<>(moviesService.getAll(), HttpStatus.OK);
	}

	@PutMapping(value = "/movies", consumes = "application/json")
	@ApiOperation(value = "Update a Movie", response = Movie.class)
	public ResponseEntity<Movie> update(@RequestBody Movie movie) {
		return new ResponseEntity<>(moviesService.update(movie), HttpStatus.OK);
	}

	@DeleteMapping(value = "/movies/{id}")
	@ApiOperation(value = "Delete a Movie")
	public void delete(@PathVariable int id) {
		moviesService.delete(id);
	}

	@GetMapping(value = "/movies/search/ratingGreaterThan/{rating}")
	@ApiOperation(value = "Find all movies with ratings which are higher than the specified value", response = List.class)
	public ResponseEntity<List<Movie>> searchByRating(@PathVariable int rating) {
		return new ResponseEntity<>(moviesService.findAllByRatingGreaterThan(rating), HttpStatus.OK);
	}

	@GetMapping(value = "/movies/search/director/{directorId}")
	@ApiOperation(value = "Find all movies with the specified Director", response = List.class)
	public ResponseEntity<List<Movie>> searchByDirectorId(@PathVariable int directorId) {
		return new ResponseEntity<>(moviesService.findAllByDirectorId(directorId), HttpStatus.OK);
	}
}
