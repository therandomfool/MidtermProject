package com.skilldistillery.brushr.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
private static EntityManagerFactory emf;
private EntityManager em;
private User user;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("BruShrPU");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		user = em.find(User.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		user=null;
	}

	@Test
	void test() {
		assertNotNull(user);
		assertEquals(1, user.getId());
		assertEquals("userguy", user.getFirstName());
		assertEquals("userlast", user.getLastName());
		assertEquals("password", user.getPassword());
		assertTrue(user.getEnabled());
		assertEquals("user", user.getRole());
//		assertEquals("admin", user.getCreatedAt());
//		assertEquals("admin", user.getUpdatedAt());
		assertEquals("user", user.getLoginName());
	}

}
