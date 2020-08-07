package com.github.dgwatts.movies.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dgwatts.movies.repos.RatingsRepo;
import com.github.dgwatts.movies.services.RatingsService;

@Service
public class RatingsServiceImpl implements RatingsService {

	private final RatingsRepo ratingsRepo;

	public RatingsServiceImpl(@Autowired RatingsRepo ratingsRepo) {
		this.ratingsRepo = ratingsRepo;
	}

	@Override
	public RatingsRepo getRepo() {
		return ratingsRepo;
	}
}
