package com.github.dgwatts.movies;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.dgwatts.movies.repos.MoviesRepo;
import com.github.dgwatts.movies.model.Movie;
import com.github.dgwatts.movies.services.MoviesService;
import com.github.dgwatts.movies.services.impl.MoviesServiceImpl;

/**
 * MoviesServiceImpl is a very thin class. This test primarily proves that
 * the service does not interfere with the data from the repo
 */
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes={MoviesServiceImpl.class})
public class MoviesServiceTests {

	@MockBean
	private MoviesRepo moviesRepo;

	@Autowired
	private MoviesService moviesService;

	private int directorCount = 0;

	Answer<Movie> createAndUpdateAnswerer = invocation -> {
		Movie m = (Movie) invocation.getArguments()[0];

		if(m.getId() == null) {
			m.setId(++directorCount);
		}
		return m;
	};

	@Test
	public void testGetAllWhenEmpty() {

		// Setup
		when(moviesRepo.findAll()).thenReturn(Collections.emptyList());

		// Test
		final List<Movie> all = moviesService.getAll();

		// Verify
		assertTrue("Should be initially empty", all.isEmpty());
		verify(moviesRepo).findAll();
	}

	@Test
	public void testGetAllWhenHasData() {
		// Setup
		final List<Movie> movies = Arrays.asList(new Movie[]{Utils.getMovie1(true), Utils.getMovie2(true), Utils.getMovie3(true), Utils.getMovie4(true)});
		when(moviesRepo.findAll()).thenReturn(movies);

		// Test
		final List<Movie> all = moviesService.getAll();

		// Verify
		assertEquals("should be the same object", all.get(0), movies.get(0));
		assertEquals("should be the same object", all.get(1), movies.get(1));
		assertEquals("should be the same object", all.get(2), movies.get(2));
		verify(moviesRepo).findAll();
	}

	@Test
	public void testGetOneWhenNotPresent() {

		// Setup
		when(moviesRepo.findById(any())).thenReturn(Optional.empty());

		// Test
		final Movie one = moviesService.getOne(1);

		// verify
		assertNull("nothing to return", one);
		verify(moviesRepo).findById(1);
	}

	@Test
	public void testGetOne() {
		// Setup
		when(moviesRepo.findById(any())).thenReturn(Optional.of(Utils.getMovie1(true)));

		// Test
		final Movie one = moviesService.getOne(1);

		// Verify
		assertNotNull("nothing to return", one);
		assertEquals("Should be the ID requested", 1, one.getId());
		verify(moviesRepo).findById(1);
	}
	
	@Test
	public void testCreate() {

		// Setup
		when(moviesRepo.save(any())).thenAnswer(createAndUpdateAnswerer);
		Movie movie1 = Utils.getMovie1(false);

		// Test
		Movie movie1Returned = moviesService.create(movie1);

		// Verify
		assertTrue("Should have an id now", movie1.getId() != null);
		assertEquals("should be the same", movie1.getTitle(), movie1Returned.getTitle());
		assertEquals("should be the same", movie1.getDirector(), movie1Returned.getDirector());

		verify(moviesRepo).save(movie1);
	}

	@Test
	public void testUpdate() {

		// Setup
		when(moviesRepo.save(any())).thenAnswer(createAndUpdateAnswerer);
		Movie movie1 = Utils.getMovie1(true);

		// Test
		Movie movie1Returned = moviesService.create(movie1);

		// Verify
		assertEquals("should be the same", movie1.getId(), movie1Returned.getId());
		// Verify
		assertEquals("should be the same", movie1.getTitle(), movie1Returned.getTitle());
		assertEquals("should be the same", movie1.getDirector(), movie1Returned.getDirector());
		assertEquals("should be the same", movie1.getRatings(), movie1Returned.getRatings());

		verify(moviesRepo).save(movie1);
	}

	@Test
	public void testDelete() {
		// Test
		moviesService.delete(1);

		// Verify
		verify(moviesRepo).deleteById(1);
	}
}
