import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Bass extends StringInstrument {
	private boolean isFretless;
	final public static int BASS_MIN_NUM_OF_STRINGS = 4, BASS_MAX_NUM_OF_STRINGS = 6;

	// Regular Constructor
	public Bass(Number price, String manufacturer, int numberOfStrings, boolean isFretless) {
		super(price, manufacturer, numberOfStrings);
		checkNumberOfStrings();
		setIsFretless(isFretless);
	}

	// Constructor that reads from Scanner
	public Bass(Scanner s) {
		super(s);
		checkNumberOfStrings();
		try {
			setIsFretless(s.nextBoolean());
		} catch (InputMismatchException ex) {
			throw new InputMismatchException("Whether a bass is fretless or not is boolean,\nany other "
					+ "string than \"True\" or \"False\" is not acceptable");
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException("Corrupted file, Bass fretless attribute is missing");
		}

	}

	// Getter for Is Fretless attribute
	public boolean getIsFretless() {
		return this.isFretless;
	}

	// Setter for Is Fretless attribute
	public void setIsFretless(boolean isFretless) {
		this.isFretless = isFretless;
	}

	// Checks if the number of strings is okay
	public void checkNumberOfStrings() {
		int numberOfStrings = getNumberOfStrings();
		if (numberOfStrings < BASS_MIN_NUM_OF_STRINGS || BASS_MAX_NUM_OF_STRINGS < numberOfStrings)
			throw new IllegalArgumentException("Bass number of strings is a number between " + BASS_MIN_NUM_OF_STRINGS
					+ " and " + BASS_MAX_NUM_OF_STRINGS);
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof Bass))
			return false;

		if (getIsFretless() != ((Bass) anotherObject).getIsFretless())
			return false;

		return super.equals(anotherObject);
	}

	@Override
	public String toString() {
		return super.toString() + "Fretless: " + (getIsFretless() ? "Yes" : "No");
	}
	
	@Override
	public Bass clone() throws CloneNotSupportedException {
		return (Bass) super.clone();
	}


}
