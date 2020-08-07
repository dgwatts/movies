package com.github.dgwatts.movies;

import com.github.dgwatts.movies.model.Director;
import com.github.dgwatts.movies.model.Movie;
import com.github.dgwatts.movies.model.Rating;

public class Utils {

	private static final String MOVIE_1 = "movie1";
	private static final String MOVIE_2 = "movie2";
	private static final String MOVIE_3 = "movie3";
	private static final String MOVIE_4 = "movie3";

	private static final String GIVEN_NAME_1 = "given1";
	private static final String GIVEN_NAME_2 = "given2";
	private static final String GIVEN_NAME_3 = "given3";

	private static final String FAMILY_NAME_1 = "family1";
	private static final String FAMILY_NAME_2 = "family2";
	private static final String FAMILY_NAME_3 = "family3";

	public static Movie getMovie1(boolean withId) {
		return getMovie(withId, 1, MOVIE_1, getDirector1(withId), 11);
	}

	public static Movie getMovie2(boolean withId) {
		return getMovie(withId, 2, MOVIE_2, getDirector2(withId), 22);
	}

	public static Movie getMovie3(boolean withId) {
		return getMovie(withId, 3, MOVIE_3, getDirector3(withId), 33);
	}

	public static Movie getMovie4(boolean withId) {
		return getMovie(withId, 4, MOVIE_4, getDirector3(withId), 44);
	}

	public static Movie getMovie(boolean withId, Integer id, String title, Director director, int rating) {
		return new Movie().setId(withId ? id : null).setTitle(title).setDirector(director);
	}

	public static Director getDirector1(boolean withId) {
		return getDirector(withId, 1, GIVEN_NAME_1, FAMILY_NAME_1);
	}

	public static Director getDirector2(boolean withId) {
		return getDirector(withId, 2, GIVEN_NAME_2, FAMILY_NAME_2);
	}

	public static Director getDirector3(boolean withId) {
		return getDirector(withId, 3, GIVEN_NAME_3, FAMILY_NAME_3);
	}

	public static Director getDirector(boolean withId, Integer id, String givenName, String familyName) {
		return new Director().setId(withId ? id : null).setGivenName(givenName).setFamilyName(familyName);
	}

	public static Rating getRating1(boolean withId) {
		return getRating(withId, 1, GIVEN_NAME_1, getMovie1(withId));
	}

	public static Rating getRating2(boolean withId) {
		return getRating(withId, 2, GIVEN_NAME_2, getMovie2(withId));
	}

	public static Rating getRating3(boolean withId) {
		return getRating(withId, 3, GIVEN_NAME_3, getMovie3(withId));
	}

	public static Rating getRating(boolean withId, Integer id, String givenName, Movie movie) {
		return new Rating().setId(withId ? id : null).setReviewerName(givenName).setMovie(movie);
	}

}
