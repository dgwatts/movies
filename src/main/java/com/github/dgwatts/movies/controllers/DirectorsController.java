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

import com.github.dgwatts.movies.services.DirectorsService;
import com.github.dgwatts.movies.model.Director;

@RestController
public class DirectorsController {

	private final DirectorsService directorsService;

	public DirectorsController(@Autowired DirectorsService directorsService) {
		this.directorsService = directorsService;
	}

	@PostMapping(value = "/directors", consumes = "application/json")
	public ResponseEntity<Director> create(@RequestBody Director director) {
		return new ResponseEntity<>(directorsService.create(director), HttpStatus.OK);
	}

	@GetMapping(value = "/directors/{id}")
	public ResponseEntity<Director> getOne(@PathVariable int id) {
		return new ResponseEntity<>(directorsService.getOne(id), HttpStatus.OK);
	}

	@GetMapping(value = "/directors")
	public ResponseEntity<List<Director>> getAll() {
		return new ResponseEntity<>(directorsService.getAll(), HttpStatus.OK);
	}

	@PutMapping(value = "/directors", consumes = "application/json")
	public ResponseEntity<Director> update(@RequestBody Director director) {
		return new ResponseEntity<>(directorsService.update(director), HttpStatus.OK);
	}

	@DeleteMapping(value = "/directors/{id}")
	public void delete(@PathVariable int id) {
		directorsService.delete(id);
	}
}
