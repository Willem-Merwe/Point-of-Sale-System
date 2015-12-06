
package coding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Willem-Merwe Joubert
 *
 */
public class UserInput {

	/**
	 * Reads in keyboard input and returns it.
	 * 
	 * @exception IOException
	 * @return User keyboard Input
	 */
	public static String stringUserInput() {
		try {
			BufferedReader userInput = new BufferedReader(
					new InputStreamReader(System.in));
			String returnString = userInput.readLine();
			return returnString;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method is undone.
	 * 
	 * @param input
	 * @return
	 */
	public static boolean stringValidation(String input) {
		boolean validation = false;
		return validation;
	}

	/**
	 * Method is undone.
	 * 
	 * @param input
	 * @return
	 */
	public static String stringNormalisation(String input) {
		String normalisedInput = null;
		return normalisedInput;
	}
}
