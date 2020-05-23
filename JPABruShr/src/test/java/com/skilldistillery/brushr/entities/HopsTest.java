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

public class HopsTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Hops hops;
		
		
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
			hops = em.find(Hops.class, 1);
		}

		@AfterEach
		void tearDown() throws Exception {
			em.close();
			hops=null;
		}

		@Test
		void test() {
			assertNotNull(hops);
			assertEquals(1, hops.getId());
			assertEquals("hops hops", hops.getName());
			assertTrue(hops.getEnabled());
			assertEquals("very hoppy", hops.getDescription());
//			assertEquals("", hops.getImgUrl());
		}

}
