package com.revature.project0;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

class Project0Test {

	@Test
	void test() {
		assertTrue(true);
	}
	@Test
	void loginTest() {
		ByteArrayInputStream in = new ByteArrayInputStream("Testy\npasw0rd".getBytes());
		System.setIn(in);
		Project0.login();
		
		assertEquals("Testy",Project0.user);
		assertEquals("pasw0rd",Project0.password);
	}
	@Test
	void goodCustomerPasswordTest() {
		ByteArrayInputStream in = new ByteArrayInputStream("Testy\npasw0rd".getBytes());
		System.setIn(in);		
		Project0.login();
		Project0.setRole();
		
		assertEquals("customer", Project0.role);
	}
	@Test
	void badCustomerPasswordTest() {
		ByteArrayInputStream in = new ByteArrayInputStream("Testy\npassw0rd".getBytes());
		System.setIn(in);
		Project0.login();
		Project0.setRole();
		
		assertEquals(null, Project0.role);
	}
	
	

}
