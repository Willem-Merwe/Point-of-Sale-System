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
import java.util.Scanner;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class Main {

	private static final String configDirectory = "resources/config/config.properties";
	private static Scanner userInput = null;

	public static void main(String[] args) {
		System.out.println("Welcome to PoS."
				+ "\nGives us a moment to get things started..." + "\n" + "\n");
		configuration();
		login();
		menu();
	}

	private static void configuration() {
		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
		userInput = new Scanner(System.in);
		String url, username, password;

		try {
			File config = new File(configDirectory);
			if (!config.exists()) {
				output = new FileOutputStream(configDirectory);
				
				System.out.println("Configuration in progress, please answer the next set of questions to setup your database."
						+ "\n"
						+ "\nWhat is your Database's location? localhost:3306/<database name>");				
				url = userInput.next();
				
				System.out.println("What is your Database username?");
				username = userInput.next();
				
				System.out.println("What is your Database password?");
				
				// set the properties value
				prop.setProperty("database",
						"jdbc:mariadb://" + url);
				prop.setProperty("dbuser", username);

				// save properties to project folder
				prop.store(output, null);
			}

			input = new FileInputStream(configDirectory);

			// load the property file
			prop.load(input);

			// get and set the property value

			DatabaseManager.setUrl(prop.getProperty("database"));
			DatabaseManager.setUser(prop.getProperty("dbuser"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void menu() {
		userInput = new Scanner(System.in);
		System.out.println("Main Menu:" + "\n1.)Sales"
				+ "\n2.)Quit");

		String input = userInput.next();
		switch (input) {
		case "1":
			sales();
			break;
		case "2":
			System.exit(1);
			break;
		default:
			menu();
			break;
		}
	}

	private static void login() {
		userInput = new Scanner(System.in);
		String input = null;
		boolean test = false;
		
		while(!test){
			System.out.println("Please enter password...");
			input = userInput.next();
			DatabaseManager.setPass(input);
			test = DatabaseManager.connectDatabase();
			if (!test){
				System.out.println("\n"
						+ "\n"
						+ "It seems the password you have entered is incorrect."
						+ "\n"
						+ "\n");
			}
		}
		System.out.println("Your password seems to be valid."
				+ "\n"
				+ "\n");
	}
	
	private static void sales(){
		
	}

}