package com.skillstorm.im.conf;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class InventoryDBCredentials {
	
	private static InventoryDBCredentials instance;
	private String url;
	private String username;
	private String password;

	private InventoryDBCredentials() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (InputStream input = InventoryDBCredentials.class.getClassLoader()
					.getResourceAsStream("application.properties")) {
				Properties props = new Properties();
				props.load(input);
				this.url = props.getProperty("db.url");
				System.out.println(this.url);
				this.username = props.getProperty("db.username");
				System.out.println(this.username);
				this.password = props.getProperty("db.password");
				System.out.println(this.password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static InventoryDBCredentials getInstance() {
		if (instance == null) {
			instance = new InventoryDBCredentials();
		}
		return instance;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}
