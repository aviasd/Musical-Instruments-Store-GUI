import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class WindInstrument extends MusicalInstrument {
	private String material;
	final public static String[] WIND_INSTRUMENTS_MATERIALS = { "Metal", "Wood", "Plastic" };

	// Regular Constructor
	public WindInstrument(Number price, String manufacturer, String material) {
		super(price, manufacturer);
		setMaterial(material);
	}

	// Constructor that reads from Scanner
	public WindInstrument(Scanner s) {
		super(s);
		try {
			setMaterial(s.next());
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException(
					"Corrupted file, " + getClass().getCanonicalName() + " material is missing");
		}
	}

	// Getter for Material attribute
	public String getMaterial() {
		return this.material;
	}

	// Setter for Material attribute
	public void setMaterial(String material) {
		material = material.substring(0, 1).toUpperCase() + material.substring(1).toLowerCase();
		if (!isKeyExistInArray(WIND_INSTRUMENTS_MATERIALS, material))
			throw new IllegalArgumentException(
					"Wind instruments are made of " + arrayAsAString(WIND_INSTRUMENTS_MATERIALS) + " only!");
		else
			this.material = material;
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof WindInstrument))
			return false;

		if (!getMaterial().equals(((WindInstrument) anotherObject).getMaterial()))
			return false;

		return super.equals(anotherObject);
	}

	@Override
	public String toString() {
		return super.toString() + String.format("Made of: %12s|  ", getMaterial());
	}

}
