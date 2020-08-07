package com.github.dgwatts.movies.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "directors", schema = "movies")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Director {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String givenName;

	private String familyName;

	public Integer getId() {
		return id;
	}

	public Director setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getGivenName() {
		return givenName;
	}

	public Director setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}

	public String getFamilyName() {
		return familyName;
	}

	public Director setFamilyName(String familyName) {
		this.familyName = familyName;
		return this;
	}
}
