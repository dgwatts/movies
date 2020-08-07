package com.github.dgwatts.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.github.dgwatts.movies.repos"})
@EntityScan({"com.github.dgwatts.movies.model"})
@SpringBootApplication
public class MockMoviesApplication {
	public static void main(String... args) {
		SpringApplication.run(MockMoviesApplication.class, args);
	}
}
