package coding;

/**
 * @author Willem-Merwe Joubert
 *
 */

public class AdminPanel {

	public static void adminPanel() {
		System.out.println("Admin Panel:" + "\n1.)Items" + "\n2.)Back");
		String menuSelection = UserInput.stringUserInput();

		switch (menuSelection) {
		case "1":
			itemPanel();
			break;
		case "2":
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
		String name;
		double price;

		System.out.print("Item Name: ");
		name = UserInput.stringUserInput();

		System.out.print("Item Price: ");
		price = Double.parseDouble(UserInput.stringUserInput());
		
		if (DatabaseManager.itemExists(name)) {
			System.out.println("Item already exists, please select new item name or edit row.");
			itemPanel();
		} else {
			DatabaseManager.insertItem(name, price);
			adminPanel();
		}
	}

	private static void editItem() {
		
	}

	private static void deleteItem() {

	}
}