/*
Copyright (c) 2013, Bernard Butler (Waterford Institute of Technology, Ireland), Project: FAME (08/SRC/I1403)
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 -  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 -  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 -  Neither the name of WATERFORD INSTITUTE OF TECHNOLOGY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package example.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import org.slf4j.LoggerFactory;

import example.domain.Family;
import example.domain.Person;

public class TestFamilyJobPerson {

	private static final org.slf4j.Logger LOG = LoggerFactory
			.getLogger(TestFamilyJobPerson.class);
	private static final String userHome = System.getProperty("user.home");
	private static final String baseDir = "work";
	private static final String project = "jpa-examples";
	private static final String projectroot = userHome + "/" + baseDir + "/"
			+ project;
	private static final String log4jProperties = projectroot
			+ "/log4j.properties";

	private static final String PERSISTENCE_UNIT_NAME = "persistenceUnit";
	private EntityManagerFactory factory;

	@Before
	public void setUp() throws Exception {
		org.apache.log4j.PropertyConfigurator.configure(log4jProperties);

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Family family;
		Person person;

		// Begin a new local transaction so that we can persist a new entity
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			// Read the existing entries
			Query q = em.createQuery("select m from Person m");
			// Persons should be empty

			// Do we have entries?
			boolean haveExistingPersons = (q.getResultList().size() > 0);
			int deletedRecords = 0;

			if (haveExistingPersons) {
				LOG.info("Have existing persons, so proceed to delete them");
//				Note that JPQL cannot delete person_family because it does
//				not map to a single Java class - use native query instead
				q = em.createNativeQuery("DELETE FROM person_family");
				deletedRecords = q.executeUpdate();
				LOG.info("Deleted {} person_family records", deletedRecords);
				q = em.createQuery("delete from Person p");
				deletedRecords = q.executeUpdate();
				LOG.info("Deleted {} person records", deletedRecords);
				q = em.createQuery("delete from Family f");
				deletedRecords = q.executeUpdate();
				LOG.info("Deleted {} family records", deletedRecords);
			}
			tx.commit();
		} finally {
			if (tx.isActive())
				tx.rollback();
		}
		
		LOG.info("Should be starting from empty tables now...");
		tx = em.getTransaction();
		try {
			tx.begin();

			family = new Family();
			family.setDescription("Family for the Knopfs");
			em.persist(family);
			for (int i = 0; i < 40; i++) {
				person = new Person();
				person.setFirstName("Jim_" + i);
				person.setLastName("Knopf_" + i);
				em.persist(person);
				family.addMember(person);
			}
			em.persist(family);
			tx.commit();

		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		// Close the EntityManager so that resources are conserved.
		em.close();

		LOG.info("Added members to Family, after close: {}",family);
	}

	@Test
	public void checkAvailablePeople() {

		// Now lets check the database and see if the created entries are there
		// Create a fresh, new EntityManager
		EntityManager em = factory.createEntityManager();

		// Perform a simple query for all the Message entities
		Query q = em.createQuery("select m from Person m");

		// We should have 40 Persons in the database
		LOG.info("Number of persons should be 40 and is {}",q.getResultList().size());
		assertTrue(q.getResultList().size() == 40);

		em.close();
	}

	@Test
	public void checkFamily() {
		EntityManager em = factory.createEntityManager();
		// Go through each of the entities and print out each of their
		// messages, as well as the date on which it was created
		Query q = em.createQuery("select f from Family f");

		// We should have one family with 40 persons
		LOG.info("Number of families should be 1 and is {}",q.getResultList().size());
		assertTrue(q.getResultList().size() == 1);
		Family family = (Family) q.getSingleResult();
		LOG.info("Family retrieved from DB is {}",family);
		List<Person> familyMembers = family.getMembers();
		int numMembers = familyMembers.size();
		LOG.info("Number of family members should be 40 and is {}", numMembers);
		assertTrue(familyMembers.size() == 40);
		em.close();
	}

	@Test(expected = javax.persistence.NoResultException.class)
	public void deletePerson() {
		EntityManager em = factory.createEntityManager();
		// Begin a new local transaction so that we can persist a new entity
		em.getTransaction().begin();
		Query q = em
				.createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName");
		q.setParameter("firstName", "Jim_1");
		q.setParameter("lastName", "Knopf_!");
		Person user = (Person) q.getSingleResult();
		em.remove(user);
		em.getTransaction().commit();
		q.getSingleResult();
		// Begin a new local transaction so that we can persist a new entity

		em.close();
	}
}
