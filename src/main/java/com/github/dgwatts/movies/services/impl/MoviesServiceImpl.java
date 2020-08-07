package com.github.dgwatts.movies.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dgwatts.movies.model.Movie;
import com.github.dgwatts.movies.repos.MoviesRepo;
import com.github.dgwatts.movies.services.MoviesService;

@Service
public class MoviesServiceImpl implements MoviesService {

	private final MoviesRepo moviesRepo;

	public MoviesServiceImpl(@Autowired MoviesRepo moviesRepo) {
		this.moviesRepo = moviesRepo;
	}

	@Override
	public MoviesRepo getRepo() {
		return moviesRepo;
	}

	@Override
	public List<Movie> findAllByRatingGreaterThan(int rating) {
		return moviesRepo.findAllByRatingGreaterThan(rating);
	}

	@Override
	public List<Movie> findAllByDirectorId(int directorId) {
		return moviesRepo.findAllByDirectorId(directorId);
	}
}

