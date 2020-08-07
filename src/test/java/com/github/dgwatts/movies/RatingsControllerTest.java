package com.github.dgwatts.movies;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.util.AssertionErrors.assertEquals;
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
import com.github.dgwatts.movies.model.Rating;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

@SpringBootTest(classes={TestConfig.class})
@AutoConfigureMockMvc
@EnableJpaRepositories(basePackages = {"com.github.dgwatts.movies.repos"})
public class RatingsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private ContentGetter cg;

	@BeforeEach
	public void setup() {
		cg = new ContentGetter();
	}

	@Test
	public void testGetAllRatings() throws Exception {
		mockMvc.perform(get("/ratings"))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Christen Schultz")))
				.andExpect(content().string(containsString("Neville Lawson")))
				.andExpect(content().string(containsString("Abigail Farley")));;

		final JsonArray rootArray = cg.getRoot().getAsJsonArray();

		assertEquals("12 reviews", 12, rootArray.size());
	}

	@Test
	public void testGetOneRating() throws Exception {
		mockMvc.perform(get("/ratings/4"))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		final JsonObject rating = cg.getRoot().getAsJsonObject();

		assertEquals("id", 4, rating.get("id").getAsInt());
		assertEquals("reviewerName", "Fletcher Carver", rating.get("reviewerName").getAsString());
		final JsonObject movie = rating.get("movie").getAsJsonObject();
		final JsonArray ratings = movie.get("ratings").getAsJsonArray();
		assertEquals("ratings size", 1, ratings.size());
		assertEquals("rating", 21, rating.get("rating").getAsInt());
		assertEquals("movie id", 4, movie.get("id").getAsInt());
	}

	@Test
	public void testGetOneUnknownRating() throws Exception {
		mockMvc.perform(get("/ratings/99"))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		final JsonNull nullObject = cg.getRoot().getAsJsonNull();
	}

	@Test
	public void testCrudOnNewRating() throws Exception {
		final int newRatingId = 13;

		mockMvc.perform(get("/ratings/" + newRatingId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		JsonNull nullObject = cg.getRoot().getAsJsonNull();

		// Create
		Rating toAdd = new Rating();
		toAdd.setReviewerName("given").setRating(56).setMovie(new Movie().setId(1));

		mockMvc.perform(
				post("/ratings")
						.contentType("application/json")
						.content(new Gson().toJson(toAdd)))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		JsonObject created = cg.getRoot().getAsJsonObject();

		assertEquals("id", newRatingId, created.get("id").getAsInt());
		assertEquals("review name", "given", created.get("reviewerName").getAsString());
		assertEquals("rating", 56, created.get("rating").getAsInt());

		// retrieve
		mockMvc.perform(get("/ratings/" + newRatingId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		JsonObject retrieved = cg.getRoot().getAsJsonObject();

		assertEquals("id", newRatingId, retrieved.get("id").getAsInt());
		assertEquals("review name", "given", created.get("reviewerName").getAsString());
		assertEquals("rating", 56, created.get("rating").getAsInt());

		// update
		toAdd.setId(retrieved.get("id").getAsInt()).setReviewerName("newName");

		mockMvc.perform(put("/ratings")
				.contentType("application/json")
				.content(new Gson().toJson(toAdd)))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		JsonObject updated = cg.getRoot().getAsJsonObject();

		assertEquals("id", newRatingId, updated.get("id").getAsInt());
		assertEquals("review name", "newName", updated.get("reviewerName").getAsString());
		assertEquals("rating", 56, updated.get("rating").getAsInt());

		// delete
		mockMvc.perform(delete("/ratings/" + newRatingId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		// Retrieve nothing
		mockMvc.perform(get("/ratings/" + newRatingId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		nullObject = cg.getRoot().getAsJsonNull();
	}
}
