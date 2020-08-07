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

import com.github.dgwatts.movies.model.Director;
import com.github.dgwatts.movies.repos.DirectorsRepo;
import com.github.dgwatts.movies.services.DirectorsService;
import com.github.dgwatts.movies.services.impl.DirectorsServiceImpl;

/**
 * DirectorsServiceImpl is a very thin class. This test primarily proves that
 * the service does not interfere with the data from the repo
 */
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes={DirectorsServiceImpl.class})
public class DirectorServiceTests {

	@MockBean
	private DirectorsRepo directorsRepo;

	@Autowired
	private DirectorsService directorsService;

	private int directorCount = 0;

	Answer<Director> createAndUpdateAnswerer = invocation -> {
		Director d = (Director) invocation.getArguments()[0];

		if(d.getId() == null) {
			d.setId(++directorCount);
		}
		return d;
	};

	@Test
	public void testGetAllWhenEmpty() {

		// Setup
		when(directorsRepo.findAll()).thenReturn(Collections.emptyList());

		// Test
		final List<Director> all = directorsService.getAll();

		// Verify
		assertTrue("Should be initially empty", all.isEmpty());
		verify(directorsRepo).findAll();
	}

	@Test
	public void testGetAllWhenHasData() {
		// Setup
		final List<Director> directors = Arrays.asList(Utils.getDirector1(true), Utils.getDirector2(true), Utils.getDirector3(true));
		when(directorsRepo.findAll()).thenReturn(directors);

		// Test
		final List<Director> all = directorsService.getAll();

		// Verify
		assertEquals("should be the same object", all.get(0), directors.get(0));
		assertEquals("should be the same object", all.get(1), directors.get(1));
		assertEquals("should be the same object", all.get(2), directors.get(2));
		verify(directorsRepo).findAll();
	}

	@Test
	public void testGetOneWhenNotPresent() {

		// Setup
		when(directorsRepo.findById(any())).thenReturn(Optional.empty());

		// Test
		final Director one = directorsService.getOne(1);

		// verify
		assertNull("nothing to return", one);
		verify(directorsRepo).findById(1);
	}

	@Test
	public void testGetOne() {
		// Setup
		when(directorsRepo.findById(any())).thenReturn(Optional.of(Utils.getDirector1(true)));

		// Test
		final Director one = directorsService.getOne(1);

		// Verify
		assertNotNull("nothing to return", one);
		assertEquals("Should be the ID requested", 1, one.getId());
		verify(directorsRepo).findById(1);
	}
	
	@Test
	public void testCreate() {

		// Setup
		when(directorsRepo.save(any())).thenAnswer(createAndUpdateAnswerer);
		Director director1 = Utils.getDirector1(false);

		// Test
		Director director1Returned = directorsService.create(director1);

		// Verify
		assertTrue("Should have an id now", director1.getId() != null);
		assertEquals("should be the same", director1.getFamilyName(), director1Returned.getFamilyName());
		assertEquals("should be the same", director1.getGivenName(), director1Returned.getGivenName());

		verify(directorsRepo).save(director1);
	}

	@Test
	public void testUpdate() {

		// Setup
		when(directorsRepo.save(any())).thenAnswer(createAndUpdateAnswerer);
		Director director1 = Utils.getDirector1(true);

		// Test
		Director director1Returned = directorsService.create(director1);

		// Verify
		assertEquals("should be the same", director1.getId(), director1Returned.getId());
		assertEquals("should be the same", director1.getFamilyName(), director1Returned.getFamilyName());
		assertEquals("should be the same", director1.getGivenName(), director1Returned.getGivenName());

		verify(directorsRepo).save(director1);
	}

	@Test
	public void testDelete() {
		// Test
		directorsService.delete(1);

		// Verify
		verify(directorsRepo).deleteById(1);
	}
}
