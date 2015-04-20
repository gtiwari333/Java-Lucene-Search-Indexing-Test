package com.gt.lucene_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private Connection conn;
	private static DBConnection singleton;
	final static String DB_DRIVER = "com.mysql.jdbc.Driver";
	final static String DATABASE = "luceneDB";
	final static String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE;
	final static String USER = DATABASE;
	final static String PASS = DATABASE;

	private Connection getConn() {
		return this.conn;
	}

	private DBConnection() throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		conn.setAutoCommit(false);

	}

	public ResultSet executeQuery(String query) throws SQLException {
		Statement stmt = getConn().createStatement();
		ResultSet rs;
		System.out.println("Query = " + query);
		rs = stmt.executeQuery(query);
		return rs;
	}

	public int executeUpdate(String query) throws SQLException {
		Statement stmt = getConn().createStatement();
		System.out.println("Update = " + query);
		int count = 0;
		count = stmt.executeUpdate(query);
		return count;
	}

	public static DBConnection getInstance() throws Exception {
		if (singleton == null) {
			singleton = new DBConnection();
		}
		return singleton;
	}
}
