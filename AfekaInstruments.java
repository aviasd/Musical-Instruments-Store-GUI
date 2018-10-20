import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AfekaInstruments {
	// Final scanner that reads from system.in
	public final static Scanner systemScanner = new Scanner(System.in);

	public static void main(String args[]) throws FileNotFoundException {
		// Loads the file from the user
		ArrayList<MusicalInstrument> afekaStore = new ArrayList<MusicalInstrument>();
		Scanner instrumentsScanner = loadFileFromUser();
		try {
			// Adds all the Instruments from the file to the Store Array List
			loadInstrumentsFromFile(instrumentsScanner, afekaStore);
		} catch (InputMismatchException ex) {
			System.err.println("\n" + ex.getMessage());
			System.exit(1);
		} catch (IllegalArgumentException ex) {
			System.err.println("\n" + ex.getMessage());
			System.exit(2);
		} catch (NoSuchElementException ex) {
			System.err.println("\n" + ex.getMessage());
			System.exit(3);
		}

		System.out.println("\nInstruments loaded from file successfully!\n");
		instrumentsScanner.close();

		// Prints all sorts of details on the Store
		StoreDetails(afekaStore);
		startInventoryMenu(afekaStore);
		System.exit(0);

	}

	// Loads the instruments from the file
	public static void loadInstrumentsFromFile(Scanner instrumentsScanner, ArrayList<MusicalInstrument> afekaStore) {
		addAllInstruments(afekaStore, loadGuitars(instrumentsScanner));
		addAllInstruments(afekaStore, loadBasses(instrumentsScanner));
		addAllInstruments(afekaStore, loadFlutes(instrumentsScanner));
		addAllInstruments(afekaStore, loadSaxophones(instrumentsScanner));
	}

	// Prints all sorts of details on the Store
	public static void StoreDetails(ArrayList<? extends MusicalInstrument> StoreList) {
		if (StoreList.size() > 0) {
			// Prints all the Instruments in the Store
			printInstruments(StoreList);
			// Prints number of different Instruments in the Store
			System.out.println("\n\n\nDifferent Instruments: " + getNumOfDifferentElements(StoreList));
			// Prints the most expensive Instrument in the Store
			System.out.println("\n\nMost Expensive Instrument:\n" + getMostExpensiveInstrument(StoreList));
		} else
			// Prints that the Store is empty
			System.out.println("There are no instruments in the store currently");
	}

	// Loads file from user. It will loop until the user will type in an
	// existing file
	public static Scanner loadFileFromUser() {
		String fileName = "";
		boolean isFindExist = false;
		Scanner instrumentsScanner = null;
		do {
			try {
				System.out.println("Please enter instruments file name / path:");
				fileName = systemScanner.next();
				instrumentsScanner = new Scanner(new File(fileName));
				isFindExist = true;

			} catch (FileNotFoundException ex) {
				System.out.println("\nFile Error! Please try again:\n\n");
			}
		} while (!isFindExist);
		return instrumentsScanner;
	}

	// Loads Guitars from Scanner
	public static ArrayList<Guitar> loadGuitars(Scanner s) {
		int numOfGuitar = getNumOfMusicalInstrumentsToBeLoaded(s, "guitars");
		ArrayList<Guitar> guitarList = new ArrayList<Guitar>();
		for (int i = 0; i < numOfGuitar; i++) {
			guitarList.add(new Guitar(s));
		}
		return guitarList;
	}

	// Loads Basses from Scanner
	public static ArrayList<Bass> loadBasses(Scanner s) {
		int numOfBass = getNumOfMusicalInstrumentsToBeLoaded(s, "basses");
		ArrayList<Bass> bassList = new ArrayList<Bass>();
		for (int i = 0; i < numOfBass; i++) {
			bassList.add(new Bass(s));
		}
		return bassList;
	}

	// Loads Flutes from Scanner
	public static ArrayList<Flute> loadFlutes(Scanner s) {
		int numOfFlute = getNumOfMusicalInstrumentsToBeLoaded(s, "flutes");
		ArrayList<Flute> fluteList = new ArrayList<Flute>();
		for (int i = 0; i < numOfFlute; i++) {
			fluteList.add(new Flute(s));
		}
		return fluteList;
	}

	// Loads Saxophones from Scanner
	public static ArrayList<Saxophone> loadSaxophones(Scanner s) {
		int numOfSaxophone = getNumOfMusicalInstrumentsToBeLoaded(s, "saxophones");
		ArrayList<Saxophone> saxophoneList = new ArrayList<Saxophone>();
		for (int i = 0; i < numOfSaxophone; i++) {
			saxophoneList.add(new Saxophone(s));
		}
		return saxophoneList;
	}

	// Reads from Scanner the number of Musical Instruments to be loaded
	public static int getNumOfMusicalInstrumentsToBeLoaded(Scanner s, String instruments) {
		int numberOfInstruments = 0;
		try {
			numberOfInstruments = s.nextInt();
		} catch (InputMismatchException ex) {
			throw new InputMismatchException("Number of " + instruments + " must be a number!");
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException("Corrupted file, number of " + instruments + " to be loaded is missing");
		}
		if (numberOfInstruments < 0)
			throw new IllegalArgumentException("Number of " + instruments + " must be a non-negative number!");
		else
			return numberOfInstruments;
	}

	// Adds one Array List to another Array List
	public static <T extends MusicalInstrument> void addAllInstruments(ArrayList<T> arrayList1,
			ArrayList<? extends T> arrayList2) {
		for (int i = 0; i < arrayList2.size(); i++) {
			arrayList1.add(arrayList2.get(i));
		}
	}

	// Print an Array List to Console
	public static void printInstruments(ArrayList<? extends MusicalInstrument> arrayList) {
		for (int i = 0; i < arrayList.size(); i++) {
			System.out.println(arrayList.get(i));
		}
	}

	// Gets the most expensive Instrument in an Array List (of Instruments)
	public static MusicalInstrument getMostExpensiveInstrument(ArrayList<? extends MusicalInstrument> arrayList) {
		MusicalInstrument mostExpensive = arrayList.get(0);
		for (int i = 0; i < arrayList.size(); i++) {
			if (mostExpensive.getPrice().doubleValue() < (arrayList.get(i)).getPrice().doubleValue())
				mostExpensive = arrayList.get(i);
		}

		return mostExpensive;
	}

	// Gets the number of different elements in an Array List
	public static int getNumOfDifferentElements(ArrayList<? extends MusicalInstrument> arrayList) {
		ArrayList<MusicalInstrument> arrayListTemp = new ArrayList<MusicalInstrument>();
		for (int i = 0; i < arrayList.size(); i++) {
			if (!arrayListTemp.contains(arrayList.get(i)))
				arrayListTemp.add(arrayList.get(i));
		}
		return arrayListTemp.size();
	}

	// The menu of the inventory
	public static void startInventoryMenu(ArrayList<MusicalInstrument> afekaStore) {
		AfekaInventory<MusicalInstrument> inventory = new AfekaInventory<MusicalInstrument>();
		String stringOptionChosen = "";
		boolean stayInMenu = true;
		do {
			printHeader("AFEKA MUSICAL INSTRUMENT INVENTORY MENU");
			printmenu();
			stringOptionChosen = systemScanner.next();
			switch (stringOptionChosen) {
			case "1":
				inventory.addAllStringInstruments(afekaStore, inventory.getInstrumentsInventory());
				System.out.println("\nAll String Instruments Added Successfully!\n");
				break;
			case "2":
				inventory.addAllWindInstruments(afekaStore, inventory.getInstrumentsInventory());
				System.out.println("\nAll Wind Instruments Added Successfully!\n");
				break;
			case "3":
				sortInventory(inventory);
				break;
			case "4":
				searchOrDeleteInstruments(inventory, "SEARCH");
				break;
			case "5":
				searchOrDeleteInstruments(inventory, "DELETE");
				break;
			case "6":
				clearInventory(inventory);
				break;
			case "7":
				printInventory(inventory);
				break;
			default:
				System.out.println("Finished!");
				stayInMenu = false;
			}
		} while (stayInMenu);
	}

	// Prints headers in the menu
	public static void printHeader(String header) {
		System.out.print("\n-------------------------------------------------------------------------\n" + header + "\n"
				+ "-------------------------------------------------------------------------\n");
	}

	// Prints the menu
	public static void printmenu() {
		System.out.print("1. Copy All String Instruments To Inventory\n" + "2. Copy All Wind Instruments To Inventory\n"
				+ "3. Sort Instruments By Brand And Price\n" + "4. Search Instrument By Brand And Price\n"
				+ "5. Delete Instrument\n" + "6. Delete all Instruments\n" + "7. Print Inventory Instruments\n"
				+ "Choose your option or any other key to EXIT\n\nYour Option: ");
	}

	// Sorts the inventory if it isn't empty
	public static void sortInventory(AfekaInventory<MusicalInstrument> inventory) {
		if (!inventory.getInstrumentsInventory().isEmpty()) {
			inventory.sortByBrandAndPrice(inventory.getInstrumentsInventory());
			System.out.println("\nInstruments Sorted Successfully!\n");
		} else
			System.out.println("\nInventory Is Empty, There Are No Instruments To Sort\n");
	}

	// Search or delete instruments - this method will ask for an instrument from
	// the user even if the inventory is empty.
	public static void searchOrDeleteInstruments(AfekaInventory<MusicalInstrument> inventory, String act) {
		int index = -1;
		System.out.println("\n" + act + " INSTRUMENT:");
		if (act.equals("DELETE") || act.equals("SEARCH")) {
			index = getIndex(inventory);
			if (index < 0) {
				System.out.println("Instrument Not Found!");
				return;
			} else
				System.out.println("Result:\n      " + inventory.getInstrumentsInventory().get(index).toString());
		}
		if (act.equals("DELETE") || act.equals("DELETE ALL")) {
			deleteOneOrAllInstrument(inventory, act, index);
		}
	}

	// Gets index of a requested instruments (by Brand and Price)
	public static int getIndex(AfekaInventory<MusicalInstrument> inventory) {
		System.out.print("Brand:");
		String brand = systemScanner.next();
		System.out.print("Price:");
		String stringPrice = systemScanner.next();
		Number price;
		try {
			price = Integer.valueOf(stringPrice);
		} catch (NumberFormatException ex) {
			try {
				price = Double.valueOf(stringPrice);
			} catch (NumberFormatException exe) {
				price = 0;
			}
		}
		return inventory.binnarySearchByBrandAndPrice(inventory.getInstrumentsInventory(), brand, price);
	}

	// Removes one or all instruments from inventory
	public static void deleteOneOrAllInstrument(AfekaInventory<MusicalInstrument> inventory, String act, int index) {
		boolean isSuccesful = false;
		boolean deleteAll = (act.equals("DELETE ALL") ? true : false);
		String stringOptionChosen = getYesOrNoFromUser();
		if (stringOptionChosen.equals("Y")) {
			if (deleteAll)
				isSuccesful = inventory.removeAll(inventory.getInstrumentsInventory());
			else
				isSuccesful = inventory.removeInstrument(inventory.getInstrumentsInventory(),
						inventory.getInstrumentsInventory().get(index));
		}

		if (isSuccesful)
			System.out.println("Instrument" + (deleteAll ? "s" : "") + " Deleted Successfully!");
		else
			System.out.println("Instrument" + (deleteAll ? "s Have" : " Has") + " Not Deleted!");

	}

	// Gets "Y" or "N" from user as an answer to a question
	public static String getYesOrNoFromUser() {
		boolean hasAnAnswer = false;
		String stringOptionChosen = "";
		do {
			System.out.print("Are You Sure?(Y/N)");
			stringOptionChosen = systemScanner.next().toUpperCase();
			if (stringOptionChosen.equals("Y") || stringOptionChosen.equals("N"))
				hasAnAnswer = true;
			else
				System.out.println("You Must Enter only \"Y\" or \"N\"");
		} while (!hasAnAnswer);
		return stringOptionChosen;
	}

	// Deletes all the instruments in the inventory if it is not empty
	public static void clearInventory(AfekaInventory<MusicalInstrument> inventory) {
		if (!inventory.getInstrumentsInventory().isEmpty())
			searchOrDeleteInstruments(inventory, "DELETE ALL");
		else
			System.out.println("\nInventory Is Empty, There Are No Instruments To Delete\n");
	}

	// Prints the inventory
	public static void printInventory(AfekaInventory<MusicalInstrument> inventory) {
		printHeader("AFEKA MUSICAL INSTRUMENTS INVENTORY");
		if (inventory.getInstrumentsInventory().isEmpty())
			System.out.println("There Is No Instruments To Show");
		System.out.print(inventory.toString());
	}

}
