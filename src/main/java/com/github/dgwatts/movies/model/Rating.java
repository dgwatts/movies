package com.github.dgwatts.movies.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ratings", schema = "movies")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String reviewerName;

	@ManyToOne
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
