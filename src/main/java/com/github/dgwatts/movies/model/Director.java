package com.github.dgwatts.movies.model;

public class Director {
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
