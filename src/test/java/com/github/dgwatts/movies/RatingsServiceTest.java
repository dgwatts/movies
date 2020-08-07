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

import com.github.dgwatts.movies.model.Rating;
import com.github.dgwatts.movies.repos.RatingsRepo;
import com.github.dgwatts.movies.services.RatingsService;
import com.github.dgwatts.movies.services.impl.RatingsServiceImpl;

/**
 * RatingsServiceImpl is a very thin class. This test primarily proves that
 * the service does not interfere with the data from the repo
 */
@ExtendWith({SpringExtension.class})
@SpringBootTest(classes={RatingsServiceImpl.class})
public class RatingsServiceTest {

	@MockBean
	private RatingsRepo ratingsRepo;

	@Autowired
	private RatingsService ratingsService;

	private int ratingsCount = 0;

	Answer<Rating> createAndUpdateAnswerer = invocation -> {
		Rating d = (Rating) invocation.getArguments()[0];

		if(d.getId() == null) {
			d.setId(++ratingsCount);
		}
		return d;
	};

	@Test
	public void testGetAllWhenEmpty() {

		// Setup
		when(ratingsRepo.findAll()).thenReturn(Collections.emptyList());

		// Test
		final List<Rating> all = ratingsService.getAll();

		// Verify
		assertTrue("Should be initially empty", all.isEmpty());
		verify(ratingsRepo).findAll();
	}

	@Test
	public void testGetAllWhenHasData() {
		// Setup
		final List<Rating> ratings = Arrays.asList(new Rating[]{Utils.getRating1(true), Utils.getRating2(true), Utils.getRating3(true)});
		when(ratingsRepo.findAll()).thenReturn(ratings);

		// Test
		final List<Rating> all = ratingsService.getAll();

		// Verify
		assertEquals("should be the same object", all.get(0), ratings.get(0));
		assertEquals("should be the same object", all.get(1), ratings.get(1));
		assertEquals("should be the same object", all.get(2), ratings.get(2));
		verify(ratingsRepo).findAll();
	}

	@Test
	public void testGetOneWhenNotPresent() {

		// Setup
		when(ratingsRepo.findById(any())).thenReturn(Optional.empty());

		// Test
		final Rating one = ratingsService.getOne(1);

		// verify
		assertNull("nothing to return", one);
		verify(ratingsRepo).findById(1);
	}

	@Test
	public void testGetOne() {
		// Setup
		when(ratingsRepo.findById(any())).thenReturn(Optional.of(Utils.getRating1(true)));

		// Test
		final Rating one = ratingsService.getOne(1);

		// Verify
		assertNotNull("nothing to return", one);
		assertEquals("Should be the ID requested", 1, one.getId());
		verify(ratingsRepo).findById(1);
	}

	@Test
	public void testCreate() {

		// Setup
		when(ratingsRepo.save(any())).thenAnswer(createAndUpdateAnswerer);
		Rating rating1 = Utils.getRating1(false);

		// Test
		Rating rating1Returned = ratingsService.create(rating1);

		// Verify
		assertTrue("Should have an id now", rating1.getId() != null);
		assertEquals("should be the same", rating1.getRating(), rating1Returned.getRating());
		assertEquals("should be the same", rating1.getReviewerName(), rating1Returned.getReviewerName());

		verify(ratingsRepo).save(rating1);
	}

	@Test
	public void testUpdate() {

		// Setup
		when(ratingsRepo.save(any())).thenAnswer(createAndUpdateAnswerer);
		Rating rating1 = Utils.getRating1(true);

		// Test
		Rating rating1Returned = ratingsService.create(rating1);

		// Verify
		assertEquals("should be the same", rating1.getId(), rating1Returned.getId());
		assertEquals("should be the same", rating1.getRating(), rating1Returned.getRating());
		assertEquals("should be the same", rating1.getReviewerName(), rating1Returned.getReviewerName());

		verify(ratingsRepo).save(rating1);
	}

	@Test
	public void testDelete() {
		// Test
		ratingsService.delete(1);

		// Verify
		verify(ratingsRepo).deleteById(1);
	}

}
