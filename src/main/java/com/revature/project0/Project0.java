package com.revature.project0;

import java.util.Scanner;

public class Project0 {

	public static String user;
	public static String password;
	public static String role;

	public static void login() {
		System.out.println("Username: ");
		Scanner sc = new Scanner(System.in);
		
		user = sc.nextLine();
		System.out.println("Password:");
		password = sc.nextLine();
	}
	public static void main(String[] args) {
		login();
	}
	public static void setRole() {
		if (password.equals("pasw0rd"))
			role = "customer";
		else
			role = null;
	}
}
