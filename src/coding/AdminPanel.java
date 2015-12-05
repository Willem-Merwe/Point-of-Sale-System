package coding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class AdminPanel {

	public static void adminPanel() {
		try {
			BufferedReader userInput = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("Admin Panel:" + "\n1.)Items"
					+ "\n2.)Categories");
			String menuSelection = userInput.readLine();
			userInput.close();

			switch (menuSelection) {
			case "1":
				itemPanel();
				break;
			case "2":
				categoryPanel();
				break;
			default:
				adminPanel();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void itemPanel() {
		try {
			BufferedReader userInput = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("Items Panel:" + "\n1.) Add items"
					+ "\n2.) Edit Items" + "\n3.) Delete Items");
			String menuSelection = userInput.readLine();
			userInput.close();

			switch (menuSelection) {
			case "1":
				addItem();
				break;
			case "2":
				editItem();
				break;
			case "3":
				deleteItem();
				break;
			default:
				adminPanel();
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addItem() {
		try {
			String name, category, price;
			BufferedReader userInput = new BufferedReader(
					new InputStreamReader(System.in));

			System.out.print("Item Name: ");
			name = userInput.readLine();

			System.out.print("Item Category");
			category = userInput.readLine();

			System.out.print("Item Price");
			price = userInput.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void editItem() {

	}

	private static void deleteItem() {

	}
	
	private static void categoryPanel(){
		
	}
}