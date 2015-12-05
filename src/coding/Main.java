/**
 * Point of Sale System with integrated SQL Server.
 */

package coding;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.util.Properties;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class Main {
	private static final String configDirectory = "resources/config/config.properties";

	/**
	 * This method tells the program to start configuration to get and set the
	 * database URL information to the configuration file.
	 * 
	 * login() is then called to receive the Database's user name and password.
	 * 
	 * After the user has filled in the correct login information the data is
	 * saved to the DatabaseManager class for further use during this session.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to PoS."
				+ "\nGives us a moment to get things started..." + "\n" + "\n");
		configuration();
		login();
		menu();
	}

	/**
	 * Configuration checks to see if the configuration file exists, if not then
	 * it will create a new one and ask the user to type in the database
	 * details.
	 * 
	 * The method then tests whether the details received work and saves the
	 * database URL to the configuration file.
	 * 
	 * When the configuration method runs and finds the configuration file it
	 * reads the database URL in for further use.
	 * 
	 * @exception IOException
	 */
	private static void configuration() {
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
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
					url = UserInput.stringUserInput();

					System.out.print("Database Username: ");
					username = UserInput.stringUserInput();
					System.out.println();

					System.out.print("Database password: ");
					password = UserInput.stringUserInput();
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
					adminPassword = HashGeneratorUtils.generateHash(UserInput
							.stringUserInput());
					System.out.println();

					System.out.print("Verify Admin Password for Application: ");
					adminPasswordVerify = UserInput.stringUserInput();
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

	/**
	 * The user is prompt for the needed login information to connect to the
	 * database. The details are tested and if they are incorrect they are
	 * prompt for the details once again.
	 * 
	 * Once the details have successfully been added the user name and password
	 * is saved to the DatabaseManager class for further use.
	 */
	private static void login() {
		String username, password;
		boolean test = false;

		while (!test) {
			System.out.print("Username: ");
			username = UserInput.stringUserInput();
			DatabaseManager.setUser(username);

			System.out.print("Password: ");
			password = UserInput.stringUserInput();
			DatabaseManager.setPass(password);

			test = DatabaseManager.testDatabaseConnection();
			if (!test) {
				System.out.println("\n" + "\n"
						+ "Incorrect Username and/or Password." + "\n" + "\n");
			}
		}
		System.out.println("Valid Username and Password." + "\n" + "\n");
	}

	/**
	 * Users are prompt for further actions leading to their respective classes.
	 * 
	 * Sales for beginning or continuing with a sales session.
	 * 
	 * Admin for adding, editing or deletion of items or Categories in the
	 * database.
	 */
	public static void menu() {
		System.out.println("Main Menu:" + "\n1.)Sales" + "\n2.)Admin Panel"
				+ "\n3.)Quit");

		String input = UserInput.stringUserInput();
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
	}
}