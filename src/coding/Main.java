/**
 * Point of Sale System with integrated SQL Server.
 */

package coding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.File;
import java.util.Properties;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class Main {
	private static final String configDirectory = "resources/config/config.properties";

	public static void main(String[] args) {
		System.out.println("Welcome to PoS."
				+ "\nGives us a moment to get things started..." + "\n" + "\n");
		configuration();
		login();
		menu();
	}

	/**
	 * 
	 */
	private static void configuration() {
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
		BufferedReader userInput = new BufferedReader(new InputStreamReader(
				System.in));
		String url = "", username = "", password;
		boolean test = false;

		try {
			File config = new File(configDirectory);
			if (!config.exists()) {
				while (!test) {
					output = new FileOutputStream(configDirectory);

					System.out
							.println("Configuration in progress, please answer the next set of questions to setup your database."
									+ "\n"
									+ "\nDatabase URL: (Default localhost:3306/<database name>)");
					url = userInput.readLine();

					System.out.print("Database Username: ");
					username = userInput.readLine();
					System.out.println();

					System.out.print("Database password: ");
					password = userInput.readLine();
					System.out.println();

					DatabaseManager.setUrl("jdbc:mariadb://" + url);
					DatabaseManager.setUser(username);
					DatabaseManager.setPass(password);

					test = DatabaseManager.testDatabaseConnection();
				}

				test = false;

				String adminPassword = "", adminPasswordVerify;

				while (!test) {
					System.out
							.print("Admin Account Password for Application: ");
					adminPassword = HashGeneratorUtils.generateHash(userInput
							.readLine());
					System.out.println();

					System.out.print("Verify Admin Password for Application: ");
					adminPasswordVerify = userInput.readLine();
					System.out.println();

					test = HashGeneratorUtils.hashCompareTest(
							adminPasswordVerify, adminPassword);
				}

				// set the properties value
				prop.setProperty("database", "jdbc:mariadb://" + url);
				prop.setProperty("adminpass", adminPassword);

				// save properties to project folder
				prop.store(output, null);
			} else {
				input = new FileInputStream(configDirectory);

				// load the property file
				prop.load(input);

				// get and set the property value
				DatabaseManager.setUrl(prop.getProperty("database"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void login() {
		try {
			BufferedReader userInput = new BufferedReader(
					new InputStreamReader(System.in));
			String username, password;
			boolean test = false;

			while (!test) {
				System.out.print("Username: ");
				username = userInput.readLine();
				DatabaseManager.setUser(username);

				System.out.print("Password: ");
				password = userInput.readLine();
				DatabaseManager.setPass(password);

				test = DatabaseManager.testDatabaseConnection();
				if (!test) {
					System.out.println("\n" + "\n"
							+ "Incorrect Username and/or Password." + "\n"
							+ "\n");
				}
			}
			System.out.println("Valid Username and Password." + "\n" + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void menu() {
		try {
			BufferedReader userInput = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("Main Menu:" 
					+ "\n1.)Sales"
					+ "\n2.)Admin Panel" 
					+ "\n3.)Quit");

			String input = userInput.readLine();
			switch (input) {
			case "1":
				SalesPanel.salesMenu();
				break;
			case "2":
				AdminPanel.adminPanel();
				break;
			case "3":
				System.exit(1);
			default:
				menu();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}