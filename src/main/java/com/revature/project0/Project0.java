package com.revature.project0;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.revature.jdbc.utils.ConnectionUtils;

public class Project0 {

	public static String user;
	public static String password;
	public static String role;

	public static void login() {
		System.out.println("Username: ");
		try (Scanner sc = new Scanner(System.in)) {
			user = sc.nextLine();
			System.out.println("Password:");
			password = sc.nextLine();
		}
	}
	public static void main(String[] args) {
		login();
		setRole();
		
		System.out.println("End of main");
	}
	public static void setRole() {
		String truePassword = null;
		String trueRole = null;
		try {
			Connection conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement getPassword = conn.prepareCall("select * from passwords where username = '"+user+"'");
			ResultSet rs = getPassword.executeQuery();
			rs.next();
			truePassword = rs.getString(2);
			trueRole = rs.getString(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (password.equals(truePassword))
			role = trueRole;
		else
			role = null;
	}
}
