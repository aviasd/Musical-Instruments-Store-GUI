import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class AfekaInventory<E extends MusicalInstrument> implements InventoryFunc<E> {
	private ArrayList<E> instrumentsInventory;
	private double sumOfPrices;
	private boolean isSorted;

	public AfekaInventory() {
		setInstrumentsInventory(new ArrayList<E>());
	}

	public AfekaInventory(ArrayList<E> listOfInstruments) {
		setInstrumentsInventory(listOfInstruments);
		setSumOfPrices(sumAllPrices(listOfInstruments));
		setIsSorted(checkIfArrayIsSortedFromIndexToEnd(listOfInstruments, 0));
	}

	public ArrayList<E> getInstrumentsInventory() {
		return instrumentsInventory;
	}

	public void setInstrumentsInventory(ArrayList<E> instrumentsInventory) {
		this.instrumentsInventory = instrumentsInventory;
	}

	public double getSumOfPrices() {
		return sumOfPrices;
	}

	public void setSumOfPrices(double sumOfPrices) {
		this.sumOfPrices = sumOfPrices;
	}

	public boolean getIsSorted() {
		return isSorted;
	}

	public void setIsSorted(boolean isSorted) {
		this.isSorted = isSorted;
	}

	@Override
	public void addAllStringInstruments(ArrayList<? extends MusicalInstrument> src,
			ArrayList<? super MusicalInstrument> dst) {
		for (int i = 0; i < src.size(); i++) {
			if (src.get(i) instanceof StringInstrument) {
				dst.add(src.get(i));
				if (dst == getInstrumentsInventory())
					updateInventoryAfterAddingOneElememt(src.get(i).getPrice());
			}
		}
	}

	@Override
	public void addAllWindInstruments(ArrayList<? extends MusicalInstrument> src,
			ArrayList<? super MusicalInstrument> dst) {
		for (int i = 0; i < src.size(); i++) {
			if (src.get(i) instanceof WindInstrument) {
				dst.add(src.get(i));
				if (dst == getInstrumentsInventory())
					updateInventoryAfterAddingOneElememt(src.get(i).getPrice());
			}
		}
	}

	@Override
	public void sortByBrandAndPrice(ArrayList<E> array) {
		Collections.sort(array);
		if (array == getInstrumentsInventory() && array.size() != 0)
			setIsSorted(true);
	}

	@Override
	public int binnarySearchByBrandAndPrice(ArrayList<E> array, String brand, Number price) {
		// This section will sort the array if it is not sorted.
		if (!getIsSorted() || array != getInstrumentsInventory())
			sortByBrandAndPrice(array);

		brand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
		int lowerBound = 0, upperBound = array.size() - 1, placeOfCloseEnough = -1;
		boolean isThereInstrumentWithSameBrand = false;
		double gap = (array != getInstrumentsInventory() ? sumAllPrices(array) : getSumOfPrices());
		gap = (gap > price.doubleValue() ? gap : price.doubleValue());

		// if there are an instruments with the same brand as requested, it will
		// return the one with the closest price. 
		// if not, it will return -1 -lowerBound
		//(the index of the requested instrument if it has been existing)
		while (upperBound >= lowerBound) {
			int mid = (lowerBound + upperBound) / 2;
			if ((array.get(mid).getBrand().compareTo(brand) == 0)) {
				isThereInstrumentWithSameBrand = true;
				double reminder = array.get(mid).getPrice().doubleValue() - price.doubleValue();
				if (reminder == 0)
					return mid;
				if (Math.abs(reminder) < gap) {
					placeOfCloseEnough = mid;
					gap = Math.abs(reminder);
				}
				if (reminder < 0)
					lowerBound = mid + 1;
				else if (reminder > 0)
					upperBound = mid - 1;
			} else {
				if (array.get(mid).getBrand().compareTo(brand) < 0) {
					lowerBound = mid + 1;
				} else if (array.get(mid).getBrand().compareTo(brand) > 0) {
					upperBound = mid - 1;
				}
				placeOfCloseEnough = (isThereInstrumentWithSameBrand ? placeOfCloseEnough : -1 - lowerBound);
			}
		}
		return placeOfCloseEnough;
	}

	// This method is not being used in the project
	@Override
	public void addInstrument(ArrayList<E> array, E instrument) {
		array.add(instrument);
		if (array == getInstrumentsInventory())
			updateInventoryAfterAddingOneElememt(instrument.getPrice());
	}

	@Override
	public boolean removeInstrument(ArrayList<E> array, E instrument) {
		boolean isAffected = array.remove(instrument);
		if (array == getInstrumentsInventory()) {
			if (isAffected)
				setSumOfPrices(sumTwoNumbers(getSumOfPrices(), 0 - instrument.getPrice().doubleValue()));
			if (array.isEmpty())
				setIsSorted(false);
		}
		return isAffected;
	}

	@Override
	public boolean removeAll(ArrayList<E> array) {
		boolean isAffected = array.removeAll(array);
		if (isAffected && array == getInstrumentsInventory()) {
			setIsSorted(false);
			setSumOfPrices(0);
		}
		return isAffected;
	}

	// If the array is the instruments inventory, this method will update the
	// sum and isSorted parameters
	public void updateInventoryAfterAddingOneElememt(Number price) {
		setSumOfPrices(sumTwoNumbers(getSumOfPrices(), price));
		// If the array is sorted, we need to check only if the last instrument
		// (the new one) is in its proper index
		if (getIsSorted())
			setIsSorted(checkIfArrayIsSortedFromIndexToEnd(getInstrumentsInventory(),
					getInstrumentsInventory().size() - 2));
	}

	// Sums all prices of an arrayList of instruments
	public double sumAllPrices(ArrayList<E> array) {
		double sum = 0;
		for (E i : array)
			sum = sumTwoNumbers(sum, i.getPrice());
		return sum;
	}

	// Sums two Numbers and returns their sum in double
	public <N extends Number> double sumTwoNumbers(N num1, N num2) {
		return num1.doubleValue() + num2.doubleValue();
	}

	// Checks weather an array is sorted or not
	public boolean checkIfArrayIsSortedFromIndexToEnd(ArrayList<E> array, int index) {
		boolean arrayIsSorted = true;
		for (int i = index; i < array.size() - 1; i++)
			if (array.get(i).compareTo(array.get(i + 1)) > 0)
				arrayIsSorted = false;
		return arrayIsSorted;
	}

	@Override
	public String toString() {
		String inventoryAsAString = "";
		for (E i : getInstrumentsInventory()) {
			inventoryAsAString += i + "\n";
		}
		inventoryAsAString += String.format(Locale.GERMANY, "\nTotal Price:%8.2f     Sorted: %b\n", getSumOfPrices(),
				getIsSorted());
		return inventoryAsAString;
	}
}
