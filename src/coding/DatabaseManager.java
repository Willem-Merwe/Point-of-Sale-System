package coding;

import java.sql.*;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class DatabaseManager {
	private static String url;
	private static String username;
	private static String password;

	public static Connection connect() {

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean testDatabaseConnection() {
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database Connected!");
			conn.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public static void setUrl(String database) {
		url = database;
	}

	public static void setUser(String user) {
		username = user;
	}

	public static void setPass(String pass) {
		password = pass;
	}

	public static void createDB() {
		System.out.println("Setting up database.");
		String itemsTableCreationSQL, salesTableCreationSQL, sessionsTableCreationSQL, ordersTableCreationSQL;

		itemsTableCreationSQL = "CREATE TABLE IF NOT EXISTS items("
				+ "itemID INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
				+ "itemName varchar(30) NOT NULL,"
				+ "itemPrice DECIMAL(15,2) NOT NULL);";

		salesTableCreationSQL = "CREATE TABLE IF NOT EXISTS sales ("
				+ "saleID INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
				+ "sessionID INT(4) NOT NULL," 
				+ "orderTotal DECIMAL(15,2) DEFAULT NULL,"
				+ "timeOfSale TIME DEFAULT NULL);";

		sessionsTableCreationSQL = "CREATE TABLE IF NOT EXISTS sessions ("
				+ "sessionID INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT," 
				+ "sessionTimeAndDateOpened DATETIME NOT NULL,"
				+ "sessionTimeAndDateClosed DATETIME DEFAULT NULL,"
				+ "sessionStanding BOOLEAN NOT NULL);";

		ordersTableCreationSQL = "CREATE TABLE IF NOT EXISTS orders ("
				+ "orderID INT(4) PRIMARY KEY NOT NULL AUTO_INCREMENT,"
				+ "saleID INT(4) NOT NULL,"
				+ "itemID INT(4) NOT NULL," 
				+ "itemQuantity INT NOT NULL);";

		updateDatabase(salesTableCreationSQL);
		updateDatabase(itemsTableCreationSQL);
		updateDatabase(sessionsTableCreationSQL);
		updateDatabase(ordersTableCreationSQL);
		System.out.println("Tables running as intended." + "\n" + "\n");
	}
	
	public static boolean itemExists(String item){
		Connection conn;
		ResultSet rs = null;
		Statement stmt = null;
		boolean exists = false;
		String existsSQL = "SELECT itemName FROM items WHERE EXISTS(SELECT 1 FROM items WHERE itemName ='" + item +"');";
		try{
			conn = connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(existsSQL);
			exists = rs.next();
			return exists;
		}catch(SQLException e){
			e.printStackTrace();
			return exists;
		}
	}
	
	public static void insertItem(String name, double price){
		String insertSQL = "INSERT INTO items (itemName, itemPrice) VALUES ('" + name + "', '" + price + "');";
		updateDatabase(insertSQL);
	}
	
	private static void updateDatabase(String sql) {
		Statement stmt = null;
		Connection conn;
		try {
			conn = connect();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
