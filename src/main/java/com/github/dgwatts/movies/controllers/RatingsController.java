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
import com.github.dgwatts.movies.model.Rating;
import com.github.dgwatts.movies.services.RatingsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="ratings",  description="Operations for Ratings")
public class RatingsController {

	private final RatingsService ratingsService;

	public RatingsController(@Autowired RatingsService ratingsService) {
		this.ratingsService = ratingsService;
	}

	@PostMapping(value = "/ratings", consumes = "application/json")
	@ApiOperation(value = "Create a new Rating", response = Rating.class)
	public ResponseEntity<Rating> create(@RequestBody Rating rating) {
		return new ResponseEntity<>(ratingsService.create(rating), HttpStatus.OK);
	}

	@GetMapping(value = "/ratings/{id}")
	@ApiOperation(value = "Retrieve a Rating", response = Rating.class)
	public ResponseEntity<Rating> getOne(@PathVariable int id) {
		return new ResponseEntity<>(ratingsService.getOne(id), HttpStatus.OK);
	}

	@GetMapping(value = "/ratings")
	@ApiOperation(value = "Retrieve all Ratings", response = List.class)
	public ResponseEntity<List<Rating>> getAll() {
		return new ResponseEntity<>(ratingsService.getAll(), HttpStatus.OK);
	}

	@PutMapping(value = "/ratings", consumes = "application/json")
	@ApiOperation(value = "Update a rating", response = Rating.class)
	public ResponseEntity<Rating> update(@RequestBody Rating rating) {
		return new ResponseEntity<>(ratingsService.update(rating), HttpStatus.OK);
	}

	@DeleteMapping(value = "/ratings/{id}")
	@ApiOperation(value = "Delete a Rating")
	public void delete(@PathVariable int id) {
		ratingsService.delete(id);
	}
}