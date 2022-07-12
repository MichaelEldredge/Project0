package com.revature.project0;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.jdbc.utils.ConnectionUtils;

class Project0Test {
	@Test
	void test() {
		assertTrue(true);
	}
	@Test
	void loginTest() {
		ByteArrayInputStream in = new ByteArrayInputStream("Testy\npasw0rd".getBytes());
		System.setIn(in);
		Project0.sc = new Scanner(System.in);
		Project0.login();
		
		assertEquals("Testy",Project0.user);
		assertEquals("pasw0rd",Project0.password);
	}
	@Test
	void multipleLoginTest() {
		ByteArrayInputStream in = new ByteArrayInputStream("Bob\n123\nTesty\npasw0rd".getBytes());
		System.setIn(in);
		Project0.sc = new Scanner(System.in);
		Project0.login();
		Project0.login();
		
		assertEquals("Testy",Project0.user);
		assertEquals("pasw0rd",Project0.password);
		
	}
	@Test
	void goodCustomerPasswordTest() {
		Project0.user = "Testy";
		Project0.password = "pasw0rd";
		
		Project0.setRole();
		assertEquals("customer", Project0.role);
	}
	@Test
	void badCustomerPasswordTest() {
		Project0.user = "Testy";
		Project0.password = "password";
		
		Project0.setRole();
		assertEquals(null, Project0.role);
	}
	
	@Test
	void goodEmployeePasswordTest() {
		Project0.user = "Boss";
		Project0.password = "Jazz";
		
		Project0.setRole();
		assertEquals("employee", Project0.role);
	}
	@Test
	void badEmployeePasswordTest() {
		Project0.user = "Boss";
		Project0.password = "Jazzzz";
		
		Project0.setRole();
		assertEquals(null, Project0.role);
	}
	@Test
	void invalidUserPasswordTest() {
		Project0.user = "Bob";
		Project0.password = "";
		
		Project0.setRole();
		assertEquals(null, Project0.role);
	}
	@Test
	void applyForNewAccountTest() {
		Project0.user = "Testy";
		String shouldBeTesty = "NotTestyYet";
		try {
			Connection conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement clearTestyApplication = conn.prepareCall("delete from account_applications where customer = 'Testy'");
			clearTestyApplication.executeUpdate();
			Project0.applyForNewAccount("checking", 100);
			CallableStatement getAccountApplication = conn.prepareCall("select * from account_applications where customer = 'Testy'");
			ResultSet rs = getAccountApplication.executeQuery();
			rs.next();
			shouldBeTesty = rs.getString(1);
		} catch (SQLException e) {
			fail("unable to get correct response from database");
			e.printStackTrace();
		}
		assertEquals("Testy",shouldBeTesty);
	}
	@Test
	void getBalanceTest() {
		Project0.user = "Testy";
		int foundBalance = Project0.getBalance("old_account");
		assertEquals(987,foundBalance);
	}
	@Test
	void addToBalanceTest() {
		Project0.user = "Testy";
		int initialBalance = Project0.getBalance("checking");
		Project0.addToBalance("checking", 1);
		assertEquals(initialBalance + 1, Project0.getBalance("checking"));
	}
	@Test
	void removeApplicationTest() {
		Project0.user = "Boss";
		Project0.applyForNewAccount("temporary", 100);
		Project0.deleteApplication("Boss", "temporary");
		Connection conn;
		int count = 0;
		
		try {
			conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement getAccountApplication = conn.prepareCall("select * from account_applications where customer = 'Boss' and account_name = 'temporary'");
			ResultSet rs = getAccountApplication.executeQuery();
			while(rs.next())
				count++;
		} catch (SQLException e) {
			fail("Connection failed");
			e.printStackTrace();
		}
		assertEquals(0,count);
	}

}
