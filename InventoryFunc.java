import java.util.ArrayList;

// inventory management interface
public interface InventoryFunc<E extends MusicalInstrument>{

	// Add all the String Instruments from one arrayList to another
	public void addAllStringInstruments(ArrayList<? extends MusicalInstrument> src, ArrayList<? super MusicalInstrument> dst);

	// Add all the Wind Instruments from one arrayList to another
	public void addAllWindInstruments(ArrayList<? extends MusicalInstrument> src, ArrayList<? super MusicalInstrument> dst);

	// Sorts arrayList of Instruments by brand and price
	public void sortByBrandAndPrice(ArrayList<E> array);

	// Binary search in an arrayList of Instruments by brand and price
	public int binnarySearchByBrandAndPrice(ArrayList<E> array, String Brand, Number price);

	// Add an instrument to an arrayList
	public void addInstrument(ArrayList<E> array, E instrument);

	// Remove an instrument from an arrayList of instruments
	public boolean removeInstrument(ArrayList<E> array, E instrument);

	// Remove all instruments from an arrayList of instruments
	public boolean removeAll(ArrayList<E> array);

}
