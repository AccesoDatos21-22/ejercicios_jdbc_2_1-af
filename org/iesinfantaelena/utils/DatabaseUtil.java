package org.iesinfantaelena.utils;

import java.sql.DriverManager;

public class Connection {
	public static final String DB_URL = "jdbc:mysql://localhost:3306/mercado";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "";
	
	public static Connection getConnection() {
		try {
			Class.forName("org.h2.Driver");
			
			return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
			
			return null;
		}
	}
}
