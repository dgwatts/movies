package com.github.dgwatts.movies.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CrudService<T> {

	CrudRepository<T, Integer> getRepo();

	default T create(T entity) {
		return getRepo().save(entity);
	}

	default List<T> getAll() {
		List<T> entities = new ArrayList<>();
		getRepo().findAll().forEach(entities::add);

		return entities;
	}

	default void delete(int id) {
		getRepo().deleteById(id);
	}

	default T getOne(int id) {
		return getRepo().findById(id).orElse(null);
	}

	default T update(T entity) {
		return getRepo().save(entity);
	}
}
