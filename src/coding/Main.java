
package coding;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class Main {

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
		Configuration.configuration();
		login();
		menu();
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