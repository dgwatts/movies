package com.github.dgwatts.movies;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.web.servlet.MockMvc;

import com.github.dgwatts.movies.model.Director;
import com.github.dgwatts.movies.model.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

@SpringBootTest(classes={TestConfig.class})
@AutoConfigureMockMvc
@EnableJpaRepositories(basePackages = {"com.github.dgwatts.movies.repos"})
public class MoviesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ContentGetter cg;

	@BeforeEach
	public void setup() {
		cg = new ContentGetter();
	}

	@Test
	public void testGetAllMovies() throws Exception {
		mockMvc.perform(get("/movies"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Frozen Mists")))
			.andExpect(content().string(containsString("Rukavina")))
			.andExpect(content().string(containsString("The Hidden Dreamer")));

		final JsonArray rootArray = cg.getRoot().getAsJsonArray();
		assertEquals("12 movies", 12, rootArray.size());

		final JsonObject movieOne = rootArray.get(0).getAsJsonObject();

		assertEquals("Movie one title", "Frozen Mists", movieOne.get("title").getAsString());
		JsonObject movieOneDirector = movieOne.get("director").getAsJsonObject();
		assertEquals("Movie one director", "Jan", movieOneDirector.get("givenName").getAsString());
		assertEquals("Movie one director", "Rukavina", movieOneDirector.get("familyName").getAsString());

		final JsonObject movieTwo = rootArray.get(1).getAsJsonObject();
		assertEquals("Movie two title", "The Hidden Dreamer", movieTwo.get("title").getAsString());
		JsonObject movieTwoDirector = movieTwo.get("director").getAsJsonObject();
		assertEquals("Movie two director", "Georgine", movieTwoDirector.get("givenName").getAsString());
		assertEquals("Movie two director", "Brown", movieTwoDirector.get("familyName").getAsString());

		final JsonObject movieEleven = rootArray.get(10).getAsJsonObject();
		assertEquals("Movie eleven title", "The Boyfriend of the Streams", movieEleven.get("title").getAsString());
		int movieElevenId = movieEleven.get("director").getAsInt();
		assertEquals("Movie one and eleven have the same director", movieOneDirector.get("id").getAsInt(), movieElevenId);
	}

	@Test
	public void testGetOneMovie() throws Exception {
		mockMvc.perform(get("/movies/1"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		final JsonObject movie = cg.getRoot().getAsJsonObject();

		assertEquals("film title", "Frozen Mists", movie.get("title").getAsString());
		JsonObject director = movie.get("director").getAsJsonObject();
		assertEquals("given name", "Jan", director.get("givenName").getAsString());
		assertEquals("family name", "Rukavina", director.get("familyName").getAsString());
		final JsonArray ratings = movie.get("ratings").getAsJsonArray();
		assertEquals("ratings count", 1, ratings.size());
		final JsonObject rating = ratings.get(0).getAsJsonObject();
		assertEquals("reviewer name", "Constant Millet", rating.get("reviewerName").getAsString());
		assertEquals("ratings", 8, rating.get("rating").getAsInt());
	}

	@Test
	public void testGetOneUnknownMovie() throws Exception {
		mockMvc.perform(get("/movies/99"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		final JsonNull nullObject = cg.getRoot().getAsJsonNull();
	}

	@Test
	public void getAllMoviesRatedOver50() throws Exception {
		mockMvc.perform(get("/movies/search/ratingGreaterThan/50"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("The Hidden Dreamer")))
			.andExpect(content().string(containsString("Snow of Witch")))
			.andExpect(content().string(containsString("The Lover of the Search")))
			.andExpect(content().string(containsString("Worlds in the Lake")))
			.andExpect(content().string(containsString("Splintered Way")))
			.andExpect(content().string(containsString("In The Morning")))
			.andExpect(content().string(containsString("The Absent Shores")));

		final JsonArray rootArray = cg.getRoot().getAsJsonArray();
		assertEquals("7 movies over 50", 7, rootArray.size());

		for(int i = 0; i < rootArray.size(); i++) {
			int highestRating = 0;
			final JsonObject movie = rootArray.get(i).getAsJsonObject();
			final JsonArray ratings = movie.get("ratings").getAsJsonArray();

			for(int j = 0; j < ratings.size(); j++) {
				final JsonObject rating = ratings.get(j).getAsJsonObject();
				highestRating = Math.max(highestRating, rating.get("rating").getAsInt());
			}

			assertTrue("rated over 50", highestRating > 50);
		}
	}

	@Test
	public void getAllMoviesByDirectorWithOneFilm() throws Exception {
		mockMvc.perform(get("/movies/search/director/5"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		final JsonArray movies = cg.getRoot().getAsJsonArray();

		assertEquals("one movie", 1, movies.size());
		JsonObject movie = movies.get(0).getAsJsonObject();
		assertEquals("movie title", "The Lover of the Search", movie.get("title").getAsString());
		JsonObject director = movie.get("director").getAsJsonObject();
		assertEquals("director id", 5, director.get("id").getAsInt());
		assertEquals("family name", "Espinosa", director.get("familyName").getAsString());
	}

	@Test
	public void getAllMoviesByDirectorWithTwoFilms() throws Exception {
		mockMvc.perform(get("/movies/search/director/1"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		final JsonArray movies = cg.getRoot().getAsJsonArray();

		assertEquals("two movies", 2, movies.size());
		JsonObject movieOne = movies.get(0).getAsJsonObject();
		assertEquals("movie title", "Frozen Mists", movieOne.get("title").getAsString());
		JsonObject movieTwo = movies.get(1).getAsJsonObject();
		assertEquals("movie title", "The Boyfriend of the Streams", movieTwo.get("title").getAsString());
		int directorId = movieTwo.get("director").getAsInt();
		assertEquals("director id", 1, directorId);
	}

	@Test
	public void getAllMoviesByDirectorWithZeroFilms() throws Exception {
		mockMvc.perform(get("/movies/search/director/13"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		final JsonArray movies = cg.getRoot().getAsJsonArray();

		assertEquals("no movies", 0, movies.size());
	}

	@Test
	public void getAllMoviesByUnknownDirector() throws Exception {
		mockMvc.perform(get("/movies/search/director/99"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		final JsonArray movies = cg.getRoot().getAsJsonArray();

		assertEquals("no movies", 0, movies.size());
	}

	@Test
	public void testCrudOnNewMovie() throws Exception {
		final int newMovieId = 13;

		mockMvc.perform(get("/movies/" + newMovieId))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		JsonNull nullObject = cg.getRoot().getAsJsonNull();

		// get a director from the db
		mockMvc.perform(get("/directors/" + "11"))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		JsonObject retrievedDirector = cg.getRoot().getAsJsonObject();

		Director director = new Gson().fromJson(retrievedDirector, Director.class);

		// Create
		Movie toAdd = new Movie();
		toAdd.setTitle("title").setDirector(director);

		mockMvc.perform(
			post("/movies")
				.contentType("application/json")
				.content(new Gson().toJson(toAdd)))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		JsonObject created = cg.getRoot().getAsJsonObject();

		assertEquals("id", newMovieId, created.get("id").getAsInt());
		assertEquals("title", "title", created.get("title").getAsString());
		assertEquals("directorid", 11, created.get("director").getAsJsonObject().get("id").getAsInt());

		// retrieve
		mockMvc.perform(get("/movies/" + newMovieId))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		JsonObject retrieved = cg.getRoot().getAsJsonObject();

		assertEquals("id", newMovieId, retrieved.get("id").getAsInt());
		assertEquals("title", "title", retrieved.get("title").getAsString());
		// no rating on this movie
		assertEquals("directorid", 11, retrieved.get("director").getAsJsonObject().get("id").getAsInt());

		// update
		toAdd.setId(retrieved.get("id").getAsInt()).setTitle("newTitle");

		mockMvc.perform(put("/movies")
			.contentType("application/json")
			.content(new Gson().toJson(toAdd)))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		JsonObject updated = cg.getRoot().getAsJsonObject();

		assertEquals("id", newMovieId, updated.get("id").getAsInt());
		assertEquals("title", "newTitle", updated.get("title").getAsString());
		updated.get("ratings").getAsJsonNull(); // no ratings
		assertEquals("directorid", 11, updated.get("director").getAsJsonObject().get("id").getAsInt());

		// delete
		mockMvc.perform(delete("/movies/" + newMovieId))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		// Retrieve nothing
		mockMvc.perform(get("/movies/" + newMovieId))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		nullObject = cg.getRoot().getAsJsonNull();
	}
}
