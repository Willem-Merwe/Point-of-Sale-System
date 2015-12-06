/**
 * 
 */
package coding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Willem-Merwe Joubert
 *
 */
public class Configuration {
	
	private static final String configDirectory = "resources/config/config.properties";
	
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
	public  static void configuration() {
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
							.print("Configuration in progress, please answer the next set of questions to setup your database."
									+ "\n"
									+ "\nDatabase URL: (Default localhost:3306/<database name>)");
					url = UserInput.stringUserInput();

					System.out.print("Database Username: ");
					username = UserInput.stringUserInput();

					System.out.print("Database password: ");
					password = UserInput.stringUserInput();

					DatabaseManager.setUrl("jdbc:mariadb://" + url);
					DatabaseManager.setUser(username);
					DatabaseManager.setPass(password);

					test = DatabaseManager.testDatabaseConnection();
				}
				
				DatabaseManager.createDB();

				test = false;

				String adminPassword = "", adminPasswordVerify;

				while (!test) {
					System.out
							.print("Admin Account Password for Application: ");
					adminPassword = HashGeneratorUtils.generateHash(UserInput
							.stringUserInput());

					System.out.print("Verify Admin Password for Application: ");
					adminPasswordVerify = UserInput.stringUserInput();

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
}
