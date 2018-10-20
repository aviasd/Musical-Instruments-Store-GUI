import java.util.Scanner;

public class Saxophone extends WindInstrument {
	final public static String SAXOPHONE_MATERIAL = "Metal";

	// Regular Constructor (with Saxophone Material, because there is only one
	// material for Saxophones)
	public Saxophone(Number price, String manufacturer) {
		super(price, manufacturer, SAXOPHONE_MATERIAL);
	}

	// Constructor that reads from Scanner
	public Saxophone(Scanner s) {
		super(s);
		checkSaxophoneMaterial();
	}

	// Checks if the Material is okay
	public void checkSaxophoneMaterial() {
		if (!getMaterial().equals(SAXOPHONE_MATERIAL))
			throw new IllegalArgumentException("Saxophones are made from " + SAXOPHONE_MATERIAL + " only!");
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof Saxophone))
			return false;

		return super.equals(anotherObject);
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
	public Saxophone clone() throws CloneNotSupportedException {
		return (Saxophone) super.clone();
	}

}
