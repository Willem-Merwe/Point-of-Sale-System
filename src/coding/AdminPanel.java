package coding;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class AdminPanel {

	public static void adminPanel() {
		System.out.println("Admin Panel:" + "\n1.)Items" + "\n2.)Categories"
				+ "\n3.)Back");
		String menuSelection = UserInput.stringUserInput();

		switch (menuSelection) {
		case "1":
			itemPanel();
			break;
		case "2":
			categoryPanel();
			break;
		case "3":
			Main.menu();
		default:
			adminPanel();
		}
	}

	private static void itemPanel() {
		System.out.println("Items Panel:" + "\n1.) Add items"
				+ "\n2.) Edit Items" + "\n3.) Delete Items" + "\n4.) Back");
		String menuSelection = UserInput.stringUserInput();

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
		case "4":
			adminPanel();
		default:
			adminPanel();
			break;
		}
	}

	private static void addItem() {
		System.out.print("Item Name: ");
		String name = UserInput.stringUserInput();

		System.out.print("Item Category: ");
		String category = UserInput.stringUserInput();

		System.out.print("Item Price: ");
		double price = Double.parseDouble(UserInput.stringUserInput());

	}

	private static void editItem() {

	}

	private static void deleteItem() {

	}

	private static void categoryPanel() {

	}
}