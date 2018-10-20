import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class MusicalInstrument implements InstrumentFunc {
	private Number price;
	private String brand;

	// Regular Constructor
	public MusicalInstrument(Number price, String brand) {
		setPrice(price);
		setBrand(brand);
	}

	// Constructor that reads from Scanner
	public MusicalInstrument(Scanner s) {
		String priceAsAString = "";

		try {
			priceAsAString = s.next();
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException("Corrupted file, " + getClass().getCanonicalName() + " price is missing");
		}

		try {
			Integer price = Integer.parseInt(priceAsAString);
			setPrice(price);
		} catch (NumberFormatException ex) {
			try {
				Double price = Double.parseDouble(priceAsAString);
				setPrice(price);
			} catch (NumberFormatException exe) {
				throw new NumberFormatException("Price must be a number!");
			}
		}

		try {
			setBrand(s.next());
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException("Corrupted file, " + getClass().getCanonicalName() + " brand is missing");
		}
	}

	// Getter for Price attribute
	public Number getPrice() {
		return this.price;
	}

	// Setter for Price attribute
	public void setPrice(Number priceOfInstrument) {
		if (priceOfInstrument.doubleValue() <= 0)
			throw new IllegalArgumentException("Price must be a positive number!");
		else
			this.price = priceOfInstrument;
	}

	// Getter for brand attribute
	public String getBrand() {
		return this.brand;
	}

	// Setter for brand attribute
	public void setBrand(String brand) {
		this.brand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
	}

	// Checks if the key exists in the Array of Strings
	public boolean isKeyExistInArray(String[] arrayString, String key) {
		boolean foundKey = false;
		for (int i = 0; i < arrayString.length; i++) {
			if (key.equals(arrayString[i])) {
				foundKey = true;
			}
		}
		return foundKey;
	}

	// Returns a String that is the printed version of an Array of Strings
	public String arrayAsAString(String[] arrayString) {
		String arrayAsAString = "";
		for (int i = 0; i < arrayString.length; i++) {
			arrayAsAString += arrayString[i];
			if (i < arrayString.length - 2)
				arrayAsAString += ", ";
			else if (i == arrayString.length - 2)
				arrayAsAString += " or ";
		}
		return arrayAsAString;
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof MusicalInstrument))
			return false;

		if (getPrice().doubleValue() != ((MusicalInstrument) anotherObject).getPrice().doubleValue())
			return false;

		if (!getBrand().equals(((MusicalInstrument) anotherObject).getBrand()))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return String.format("%-8s %-9s| Price: " + (getPrice() instanceof Integer ? "%7d" : "%7.1f") + ", ",
				getBrand(), getClass().getCanonicalName(), getPrice());
	}

	@Override
	public int compareTo(MusicalInstrument anotherMusicalInstrument) {
		int ans = getBrand().compareTo((anotherMusicalInstrument).getBrand());
		if (ans == 0) {
			ans = (Double.valueOf(getPrice().doubleValue()))
					.compareTo(Double.valueOf(anotherMusicalInstrument.getPrice().doubleValue()));
		}
		return ans;
	}
}
