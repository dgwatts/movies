package com.github.dgwatts.movies.model;

public class Rating {
	private Integer id;

	private String reviewerName;

	private Movie movie;

	private int rating;

	public Integer getId() {
		return id;
	}

	public Rating setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public Rating setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
		return this;
	}

	public Movie getMovie() {
		return movie;
	}

	public Rating setMovie(Movie movie) {
		this.movie = movie;
		return this;
	}

	public int getRating() {
		return rating;
	}

	public Rating setRating(int rating) {
		this.rating = rating;
		return this;
	}
}
