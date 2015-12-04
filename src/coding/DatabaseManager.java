/**
 * 
 */
package coding;

import java.sql.*;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class DatabaseManager {
	private static Connection conn = null;
	private static String url;
	private static String username;
	private static String password;

	public static boolean connectDatabase() {

		System.out.println("Connecting to database...");

		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			createDB();
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

	private static void createDB() {
		System.out.println("Setting up database.");
		String accountsTableCreationSQL, salesTableCreationSQL;

		accountsTableCreationSQL = "CREATE TABLE IF NOT EXISTS accounts ("
				+ "userID int(5) NOT NULL AUTO_INCREMENT,"
				+ "username varchar(250) DEFAULT NULL,"
				+ "password varchar(250) DEFAULT NULL," + "PRIMARY KEY(userID)"
				+ ");";

		salesTableCreationSQL = "CREATE TABLE IF NOT EXISTS sales ("
				+ "saleID int(5) NOT NULL AUTO_INCREMENT,"
				+ "items int(5) DEFAULT NULL," + "total DECIMAL DEFAULT NULL,"
				+ "PRIMARY KEY(saleID)" + ");";

		updateDatabase(accountsTableCreationSQL);
		updateDatabase(salesTableCreationSQL);
		System.out.println("Tables running as intended." + "\n" + "\n");
	}

	public static void updateDatabase(String sql) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
