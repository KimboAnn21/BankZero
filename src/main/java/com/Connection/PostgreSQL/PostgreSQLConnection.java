package com.Connection.PostgreSQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


 public class PostgreSQLConnection {
	
	 private static Connection connection;
		
	 	public static Connection getConnection() throws SQLException, ClassNotFoundException {
			if (connection == null) {
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Driver not registered.");
				e.printStackTrace();
				}
			}
			connection = DriverManager.getConnection("jdbc:postgresql://trainingkimbo-bank-database.cwhxz8hojig9.us-east-2.rds.amazonaws.com:5432/bankdemodb",
				System.getenv("dbUsername"),
				System.getenv("dbPassword"));
		 
			if (connection.isClosed()) {
				System.out.println("Opening new connection...");
				connection = DriverManager.getConnection("jdbc:postgresql://trainingkimbo-bank-database.cwhxz8hojig9.us-east-2.rds.amazonaws.com:5432/bankdemodb",
						System.getenv("dbUsername"), System.getenv("dbPassword"));
		}
			return connection;
	}
		
}