package com.github.dgwatts.movies.model;

import java.util.List;

public class Movie {
	private Integer id;

	private String title;

	private Director director;

	private List<Rating> ratings;

	public Integer getId() {
		return id;
	}

	public Movie setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Movie setTitle(String title) {
		this.title = title;
		return this;
	}

	public Director getDirector() {
		return director;
	}

	public Movie setDirector(Director director) {
		this.director = director;
		return this;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public Movie setRating(List<Rating> rating) {
		this.ratings = rating;
		return this;
	}
}
