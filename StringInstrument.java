import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class StringInstrument extends MusicalInstrument {
	private int numberOfStrings;

	// Regular Constructor
	public StringInstrument(Number price, String manufacturer, int numberOfStrings) {
		super(price, manufacturer);
		setNumberOfStrings(numberOfStrings);
	}

	// Constructor that reads from Scanner
	public StringInstrument(Scanner s) {
		super(s);
		try {
			setNumberOfStrings(s.nextInt());
		} catch (InputMismatchException ex) {
			throw new InputMismatchException("Number of strings must be a number, any other input is not acceptable");
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException(
					"Corrupted file, " + getClass().getCanonicalName() + " number of strings is missing");
		}
	}

	// Getter for Number Of Strings attribute
	public int getNumberOfStrings() {
		return this.numberOfStrings;
	}

	// Setter for Number Of Strings attribute
	public void setNumberOfStrings(int numberOfStrings) {
		if (numberOfStrings >= 0)
			this.numberOfStrings = numberOfStrings;
		else
			throw new IllegalArgumentException("Number of strings must be a non-negative number!");
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof StringInstrument))
			return false;

		if (getNumberOfStrings() != ((StringInstrument) anotherObject).getNumberOfStrings())
			return false;

		return super.equals(anotherObject);
	}

	@Override
	public String toString() {
		return super.toString() + String.format("Number of strings: %2d|  ", getNumberOfStrings());
	}
}
