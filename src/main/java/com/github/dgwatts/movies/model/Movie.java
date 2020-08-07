package com.github.dgwatts.movies.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "movies", schema = "movies")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String title;

	@ManyToOne
	private Director director;

	@OneToMany(mappedBy = "movie")
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
