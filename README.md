jpa-examples
============

Example JPA implementations with different conditions: persistence providers, datastores, etc. 

## Introduction

The purpose of this project is to give some examples of JPA (Java Persistence API) for learning purposes.

It draws upon many of the existing tutorials/examples, notably from

* Lars Vogel (<http://www.vogella.com/articles/JavaPersistenceAPI/article.html>)
* datanucleus (a persistence provider, <http://www.datanucleus.org>)
* Java2s JPA tutorials (<http://www.java2s.com/Tutorial/Java/0355__JPA/Catalog0355__JPA.htm>)

and others.

One of the difficulties is that each tutorial shows how JPA can be used with particular conditions, notably persistence provider, data store type and emphasising particular data model features.

Trying to extract the general principles and to build a more realistic persistence implementation can be difficult.

## Scope

Initially, this project will focus on two persistence providers

* eclipselink
* datanucleus

and two relational database datastores

* Apache Derby
* HSQL

For the datanucleus persistence provider, there is also the option of using the neo4j datastore (<http://www.neo4j.org>).

The excellent tutorial by Lars Vogel (originally written for the eclipselink and Apache Derby combination) will be used as a basis for this project. Other tutorials will be added later.

## Installation

You need to clone the project to a local directory. The default setup is to use the work directory but that can be changed by editing the test programs (main classes).

For example, the default download steps (for a Unix-like operating system) would be

	cd ~/work
	git clone https://github.com/flatpack/jpa-examples.git

To use the project you will need both Java 5+ and maven 2+. If using neo4j server, you should download this too. Other dependencies are managed by maven.

## Usage

\# Assuming the project is installed as described above:

	cd ~/work/jpa-examples

\# Choose which combination of conditions you need. For example, using eclipselink and derby:

	git branch eclipselink-derby

\# Compile (and enhance if necessary) the Java classes

	mvn clean compile

\# For datanucleus provider and rdbms (derby and hsql) data stores only, create the schema. Eclipselink creates the schema if persistence.xml specifies that it should

	mvn datanucleus:schema-create

\# Run the unit tests (if any)

	mvn test

\# When using the datanucleus-neo4j combination, there are problems with the test maven goal. As a workaround, the tests are rewritten in  a standard Java class `FamilyPersonJob` and run from an explicit main program. The name of the main class can be added to the pom, in which case the tests can be run as follows:

	mvn exec:java

That program has been instrumented with additional logging statements to indicate whether it is working correctly or not. For the moment, there seems to be a problem with the datanucleus-neo4j plugin, in that it complains that no <Person> objects are returned after deleting them from the database (there should not be any such objects!) but then it decides to stop execution.

## Expected results

All unit tests pass and main programs give expected results.

