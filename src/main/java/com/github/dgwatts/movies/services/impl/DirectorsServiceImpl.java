package com.github.dgwatts.movies.services.impl;

import com.github.dgwatts.movies.services.DirectorsService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.github.dgwatts.movies.repos.DirectorsRepo;
import com.github.dgwatts.movies.model.Director;
import com.github.dgwatts.movies.services.DirectorsService;

@Service
public class DirectorsServiceImpl implements DirectorsService {

	private final DirectorsRepo directorsRepo;

	public DirectorsServiceImpl(@Autowired DirectorsRepo directorsRepo) {
		this.directorsRepo = directorsRepo;
	}

	@Override
	public DirectorsRepo getRepo() {
		return directorsRepo;
	}
}