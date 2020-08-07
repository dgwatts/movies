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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

@SpringBootTest(classes={TestConfig.class})
@AutoConfigureMockMvc
@EnableJpaRepositories(basePackages = {"com.github.dgwatts.movies.repos"})
public class DirectorsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ContentGetter cg;

	@BeforeEach
	public void setup() {
		cg = new ContentGetter();
	}

	@Test
	public void testGetAllDirectors() throws Exception {
		mockMvc.perform(get("/directors"))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Jan")))
				.andExpect(content().string(containsString("Rukavina")))
				.andExpect(content().string(containsString("Steve")));
	}

	@Test
	public void testGetOneDirector() throws Exception {
		mockMvc.perform(get("/directors/9"))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		final JsonObject director = cg.getRoot().getAsJsonObject();

		assertEquals("id", 9, director.get("id").getAsInt());
		assertEquals("given name", "Muhammad", director.get("givenName").getAsString());
		assertEquals("family name", "Barker", director.get("familyName").getAsString());
	}

	@Test
	public void testGetOneUnknownDirector() throws Exception {
		mockMvc.perform(get("/directors/99"))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		final JsonNull nullObject = cg.getRoot().getAsJsonNull();
	}

	@Test
	public void testCrudOnNewDirector() throws Exception {
		final int newDirectorId = 12;

		mockMvc.perform(get("/directors/" + newDirectorId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		JsonNull nullObject = cg.getRoot().getAsJsonNull();

		// Create
		Director toAdd = new Director();
		toAdd.setGivenName("given").setFamilyName("family");

		mockMvc.perform(
				post("/directors")
				.contentType("application/json")
				.content(new Gson().toJson(toAdd)))
			.andDo(print())
			.andDo(cg)
			.andExpect(status().isOk());

		JsonObject created = cg.getRoot().getAsJsonObject();

		assertEquals("id", newDirectorId, created.get("id").getAsInt());
		assertEquals("given name", "given", created.get("givenName").getAsString());
		assertEquals("family name", "family", created.get("familyName").getAsString());

		// retrieve
		mockMvc.perform(get("/directors/" + newDirectorId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		JsonObject retrieved = cg.getRoot().getAsJsonObject();

		assertEquals("id", newDirectorId, retrieved.get("id").getAsInt());
		assertEquals("given name", "given", retrieved.get("givenName").getAsString());
		assertEquals("family name", "family", retrieved.get("familyName").getAsString());

		// update
		toAdd.setId(retrieved.get("id").getAsInt()).setGivenName("newName");

		mockMvc.perform(put("/directors")
				.contentType("application/json")
				.content(new Gson().toJson(toAdd)))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		JsonObject updated = cg.getRoot().getAsJsonObject();

		assertEquals("id", newDirectorId, updated.get("id").getAsInt());
		assertEquals("given name", "newName", updated.get("givenName").getAsString());
		assertEquals("family name", "family", updated.get("familyName").getAsString());

		// delete
		mockMvc.perform(delete("/directors/" + newDirectorId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		// Retrieve nothing
		mockMvc.perform(get("/directors/" + newDirectorId))
				.andDo(print())
				.andDo(cg)
				.andExpect(status().isOk());

		nullObject = cg.getRoot().getAsJsonNull();
	}
}
