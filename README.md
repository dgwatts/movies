# movies
Movie Catalogue

## Non-functional Requirements

* Java 8
* Maven
* Spring
* Git

## Environment Assumptions

* Springboot 2.3.1, jar packaging

## Non-functional Assumptions

* All components running locally, single user mode, within an IDE, as tests
* No need to distinguish users
* No security requirements (http, DB credentials stored in a local config file)
* No need for i81n / l10n / a11y
* DB will be preseeded with data about movies and directors

## Functional Assumptions

* Movies have one director
  * If this had to be changed, a MovieDirector join table could be added
* Movies have zero or more ratings
  * If a movie has zero or 1 ratings, it could be added as a field of Movie
* Ratings have a name of the person who rated, a rating (integer 0 - 100), and a movie it applies to
  * If you wanted to add the concept of a Reviwer, a Reviewer table and MovieReview join table could be added
* A director can direct multiple movies
* Movies and directors can have non-unique names
  * Additional information will be required, to be able to fully distinguish them

## Tools used

* Springboot Initializr (https://start.spring.io/)
* H2 (https://www.h2database.com/html/main.html)
* Liquibase (https://www.liquibase.org/)
* PlantUML (https://hub.docker.com/r/plantuml/plantuml-server/)
* Title Generator (https://www.ruggenberg.nl/titels.html)
* Data Generator (https://www.generatedata.com/)