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
	public static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		role = null;
		while(role == null) {
			login();
			setRole();
		}
		int task = 0;
		if (role.equals("customer")) {
			System.out.println("If you would like to apply for a new account enter 1.");
			System.out.println("If you would like to view an existing account enter 2.");
			System.out.println("If you would like to make a withdrawal enter 3.");
			System.out.println("If you would like to make a deposit enter 4.");
			task = Integer.parseInt(sc.nextLine());
			switch(task) {
			case 1: {// new account request
				System.out.println("What would you like to call your new account?");
				String accountName = sc.nextLine();
				System.out.println("How much do you want to deposit?");
				int deposit = Integer.parseInt(sc.nextLine());
				applyForNewAccount(accountName, deposit);
				System.out.println("Account request logged.");
				break;
			}
			case 2: {// view current balance
				System.out.println("What is the name of your account?");
				String accountName = sc.nextLine();
				System.out.println("Your current balance is " + getBalance(accountName));
				break;
			}
			case 3: { // Make a withdrawal
				System.out.println("Which account would you like to make a withdrawal from?");
				String accountName = sc.nextLine();
				System.out.println("How much would you like to like to withdrawal?");
				int withdrawl = Integer.parseInt(sc.nextLine());
				addToBalance(accountName,-1*withdrawl);
				break;
			}
			case 4:{ // Make a deposit
				System.out.println("Which account would you like to make a deposit to?");
				String accountName = sc.nextLine();
				System.out.println("How much would you like to like to deposit?");
				int deposit = Integer.parseInt(sc.nextLine());
				addToBalance(accountName,deposit);
				break;
			}
			default:
				System.out.println("Not a real transaction");
			}
		if (role.equals("employee")) {
			System.out.println("PLACEHOLDER TEXT");
			task = Integer.parseInt(sc.nextLine());
			switch(task) {
			case 1:
				System.out.println("TASK 1");
				break;
			default:
				System.out.println("Not a real task");
			}
			
		}
			
		}
		sc.close();
		System.out.println("End of main");
	}
	
	public static void login() {
		System.out.println("Username: ");
		user = sc.nextLine();
		System.out.println("Password:");
		password = sc.nextLine();
	}
	
	public static void setRole() {
		String truePassword = null;
		String trueRole = null;
		String toPrint = null;
		try {
			Connection conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement getPassword = conn.prepareCall("select * from passwords where username = '"+user+"'");
			ResultSet rs = getPassword.executeQuery();
			rs.next();
			truePassword = rs.getString(2);
			trueRole = rs.getString(3);
		} catch (SQLException e) {
			role = null;
			toPrint = "Incorrect username";
		}
		if(toPrint == null) {
			if (password.equals(truePassword)) {
				role = trueRole;
				toPrint = "Login sucessful your role is "+role;
			}
			else {
				role = null;
				toPrint = "Incorrect password";
			}
		}
		System.out.println(toPrint);
	}


	public static void applyForNewAccount(String accountType, int amount) {
		try {
			Connection conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement clearTestyApplication = conn.prepareCall("insert into account_applications values ('"+user+"', '"+accountType+"', '"+amount+"')");
			clearTestyApplication.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static int getBalance(String accountName) {
		int balance = 0;
		try {
			Connection conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement getBalance = conn.prepareCall("select balance from accounts where account_owner = '"+user+"' and account_name = '"+accountName+"'");
			ResultSet rs = getBalance.executeQuery();
			rs.next();
			balance = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return balance;
	}

	public static void addToBalance(String account, int i) {
		int newbalance = getBalance(account) + i;
		try {
			Connection conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement addToBalance = conn.prepareCall(
					"update accounts set balance = "+newbalance+" where account_owner = '"+user+"' and account_name = '"+account+"'");
			addToBalance.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void deleteApplication(String accountOwner, String accountName) {
		try {
			Connection conn = ConnectionUtils.getInstance().getConnection();
			CallableStatement delApp = conn.prepareCall("delete from account_applications where customer = 'Boss' and account_name = 'temporary'");
			delApp.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
}
