package coding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

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
			Connection conn = DriverManager.getConnection(url, username,
					password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean testDatabaseConnection() {

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
			return true;

		} catch (SQLException e) {

			return false;

		} finally {
			try {

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
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
				+ "saleID INT(4) NOT NULL," + "itemID INT(4) NOT NULL,"
				+ "itemQuantity INT NOT NULL);";

		updateDatabase(salesTableCreationSQL);
		updateDatabase(itemsTableCreationSQL);
		updateDatabase(sessionsTableCreationSQL);
		updateDatabase(ordersTableCreationSQL);
		System.out.println("Tables running as intended." + "\n" + "\n");
	}

	public static boolean itemExists(String item) {

		boolean exists = false;
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT itemName FROM items WHERE EXISTS(SELECT 1 FROM items WHERE itemName =?)";

		try {

			conn = connect();
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, item);

			ResultSet rs = preparedStatement.executeQuery();

			exists = rs.next();
			return exists;

		} catch (SQLException e) {

			e.printStackTrace();
			return exists;

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}

				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

		}
	}

	public static void insertItem(String name, double price) {

		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String sql = "INSERT INTO items(itemName, itemPrice) VALUES(?, ?)";

		try {

			conn = connect();
			preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, price);

			// execute insert SQL statement
			preparedStatement.executeUpdate();

			System.out
					.println("Item has been inserted into the database successfully.");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {
			try {

				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {

				e.printStackTrace();

			}
		}
	}

	private static void updateDatabase(String sql) {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = connect();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {

				if (conn != null) {
					conn.close();
				}

				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
