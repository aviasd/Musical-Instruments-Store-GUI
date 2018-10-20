import java.util.Arrays;
import java.util.InputMismatchException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddInstrumentsPane extends VBox {
	// finals
	final ObservableList<String> INSTRUMENTS_LIST = FXCollections
			.observableList(Arrays.asList("Guitar", "Bass", "Flute", "Saxophone"));
	final int GUITAR_INDEX = 0, BASS_INDEX = 1, FLUTE_INDEX = 2, SAXOPHONE_INDEX = 3;
	final Insets PADDING = new Insets(10, 10, 10, 10);
	final int INSTRUMENTS_PANE_SPACEING = 10, BUTTON_BOX_SPACING = 20;

	// Nodes
	private ComboBox<String> instrumentTypeCombo = new ComboBox<>(INSTRUMENTS_LIST);
	private Button addButton = new Button("Add");
	private Button cancelButton = new Button("Cancel");

	// Pane
	private MusicalInstrumentAddBox musicalInstrumentAddBox;
	private HBox buttonBox = new HBox();

	// Constructor
	public AddInstrumentsPane() {
		// align pane
		setAlignment(Pos.CENTER);
		setSpacing(INSTRUMENTS_PANE_SPACEING);
		setPadding(PADDING);

		// set button box
		getButtonBox().getChildren().addAll(getAddButton(), getCancelButton());
		getButtonBox().setSpacing(BUTTON_BOX_SPACING);
		getButtonBox().setAlignment(Pos.CENTER);

		// set instrument type combo
		getInstrumentTypeCombo().setPromptText("Choose Instrument Type Here");

		// set children to place
		getChildren().addAll(getInstrumentTypeCombo(), getButtonBox());

		// set add button
		getAddButton().managedProperty().bind(getAddButton().visibleProperty());
		getAddButton().setVisible(false);

		// set instrument combo listener
		setInstrumentComboListener();
	}

	// Getters and Setters
	public ComboBox<String> getInstrumentTypeCombo() {
		return instrumentTypeCombo;
	}

	public Button getAddButton() {
		return addButton;
	}

	public MusicalInstrumentAddBox getMusicalInstrumentAddBox() {
		return musicalInstrumentAddBox;
	}

	public HBox getButtonBox() {
		return buttonBox;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	// set the musical instrument add box
	private void setMusicalInstrumentAddBox(int index) {
		switch (index) {
		case GUITAR_INDEX:
			musicalInstrumentAddBox = new GuitarAddBox();
			break;
		case BASS_INDEX:
			musicalInstrumentAddBox = new BassAddBox();
			break;
		case FLUTE_INDEX:
			musicalInstrumentAddBox = new FluteAddBox();
			break;
		case SAXOPHONE_INDEX:
			musicalInstrumentAddBox = new SaxophoneAddBox();
			break;
		default:
			return;
		}

		BorderPane.setAlignment(getMusicalInstrumentAddBox(), Pos.CENTER);
		getChildren().add(1, getMusicalInstrumentAddBox());
		getAddButton().setVisible(true);
	}

	// set listeners
	private void setInstrumentComboListener() {
		getInstrumentTypeCombo().setOnAction(e -> {
			if (getChildren().size() == 3)
				getChildren().remove(1);
			setMusicalInstrumentAddBox(INSTRUMENTS_LIST.indexOf(getInstrumentTypeCombo().getValue()));
		});
	}

	// inner class - Musical Instrument Add Box
	public abstract class MusicalInstrumentAddBox extends GridPane {
		// finals
		final Insets PADDING = new Insets(10, 10, 10, 10);
		final int VERTICAL_GAP = 8, HORIZONTAL_GAP = 10;

		// variables
		private int nextAvailableRow;

		// Nodes
		private Label brandLable = new Label("Brand:");
		private TextField brandTextField = new TextField();;
		private Label priceLable = new Label("Price:");
		private TextField priceTextField = new TextField();

		// Constructor
		public MusicalInstrumentAddBox() {
			// set box
			setVgap(VERTICAL_GAP);
			setHgap(HORIZONTAL_GAP);
			setAlignment(Pos.CENTER);

			// add rows to grid
			addRow(getNextAvailableRow(), getBrandLable(), getBrandTextField());
			setNextAvailableRow(getNextAvailableRow() + 1);
			addRow(getNextAvailableRow(), getPriceLable(), getPriceTextField());
			setNextAvailableRow(getNextAvailableRow() + 1);
		}

		// Getters and Setters
		public void setNextAvailableRow(int nextAvailableRow) {
			this.nextAvailableRow = nextAvailableRow;
		}

		public int getNextAvailableRow() {
			return nextAvailableRow;
		}

		public Label getBrandLable() {
			return brandLable;
		}

		public TextField getBrandTextField() {
			return brandTextField;
		}

		public Label getPriceLable() {
			return priceLable;
		}

		public TextField getPriceTextField() {
			return priceTextField;
		}

		// checks if there are no empty fields
		public void checkIfNoEmptyFields() {
			if (getBrandTextField().getText().isEmpty())
				throw new InputMismatchException("You cannot leave empty fields.\r\nPlease enter brand");
			if (getPriceTextField().getText().isEmpty())
				throw new InputMismatchException("You cannot leave empty fields.\r\nPlease enter price");
		}

		// convert price from string to double
		public Double convertPriceFromStringToDouble(String stringPrice) {
			Double price;
			try {
				price = Double.valueOf(stringPrice);
			} catch (NumberFormatException ex) {
				throw new InputMismatchException("The price you have eneterd is not valid.\r\nPrice must be a number");
			}
			return price;
		}

		// abstract method - make new instrument by attributes
		public abstract MusicalInstrument makeNewInstrumentByAttributes();

	}

	// inner class - String Instrument Add Box
	public abstract class StringInstrumentAddBox extends MusicalInstrumentAddBox {
		// Nodes
		private Label numberOfStringsLable = new Label("Number Of Strings:");
		private TextField numberOfStringsTextField = new TextField();;

		// Constructor
		public StringInstrumentAddBox() {
			super();

			// add row to grid
			addRow(getNextAvailableRow(), getNumberOfStringsLable(), getNumberOfStringsTextField());
			setNextAvailableRow(getNextAvailableRow() + 1);
		}

		// Getters and Setters
		public Label getNumberOfStringsLable() {
			return numberOfStringsLable;
		}

		public TextField getNumberOfStringsTextField() {
			return numberOfStringsTextField;
		}

		// convert number of strings from string to integer
		public Integer convertNumberOfStringsFromStringToInteger(String stringNumberOfStrings) {
			Integer NumberOfStrings;
			try {
				NumberOfStrings = Integer.valueOf(stringNumberOfStrings);
			} catch (NumberFormatException ex) {
				throw new InputMismatchException(
						"The number of strings you have eneterd is not valid.\r\nNumber of strings must be an integer");
			}
			return NumberOfStrings;
		}

		// checks if there are no empty fields
		public void checkIfNoEmptyFields() {
			super.checkIfNoEmptyFields();
			if (getNumberOfStringsTextField().getText().isEmpty())
				throw new InputMismatchException("You cannot leave empty fields.\r\nPlease enter number of strings");
		}
	}

	// inner class - Guitar Add Box
	public class GuitarAddBox extends StringInstrumentAddBox {
		// finals
		final ObservableList<String> GUITAR_TYPES = FXCollections
				.observableList(Arrays.asList("Electric", "Acoustic", "Classical"));

		// Nodes
		private Label guitarTypeLable = new Label("Guitar Type:");
		private ComboBox<String> guitarTypeCombo = new ComboBox<>(GUITAR_TYPES);

		// Constructor
		public GuitarAddBox() {
			super();

			// set Nodes
			getBrandTextField().setPromptText("Ex: Gibson");
			getPriceTextField().setPromptText("Ex: 7500");
			getNumberOfStringsTextField().setPromptText("Ex: 6");
			getGuitarTypeCombo().setPromptText("Type");

			// add row to grid
			addRow(getNextAvailableRow(), getGuitarTypeLable(), getGuitarTypeCombo());
			setNextAvailableRow(getNextAvailableRow() + 1);
		}

		// Getters
		public Label getGuitarTypeLable() {
			return guitarTypeLable;
		}

		public ComboBox<String> getGuitarTypeCombo() {
			return guitarTypeCombo;
		}

		// checks if there are no empty fields
		public void checkIfNoEmptyFields() {
			super.checkIfNoEmptyFields();
			if (GUITAR_TYPES.indexOf(getGuitarTypeCombo().getValue()) < 0)
				throw new InputMismatchException("You cannot leave empty fields.\r\nPlease choose guitar type");
		}

		// make new instrument by attributes
		public Guitar makeNewInstrumentByAttributes() {
			checkIfNoEmptyFields();
			Double price = convertPriceFromStringToDouble(getPriceTextField().getText());
			Integer numberOfStrings = convertNumberOfStringsFromStringToInteger(
					(getNumberOfStringsTextField().getText()));
			return new Guitar(price, getBrandTextField().getText(), numberOfStrings, getGuitarTypeCombo().getValue());
		}
	}

	// inner class - Bass Add Box
	public class BassAddBox extends StringInstrumentAddBox {
		// Nodes
		private Label fretlessLable = new Label("Fretless:");
		private CheckBox fretlessCheckBox = new CheckBox();

		// Constructor
		public BassAddBox() {
			super();

			// set Nodes
			getBrandTextField().setPromptText("Ex: Fender Jazz");
			getPriceTextField().setPromptText("Ex: 7500");
			getNumberOfStringsTextField().setPromptText("Ex: 4");

			// add row to grid
			addRow(getNextAvailableRow(), getFretlessLable(), getFretlessCheckBox());
			setNextAvailableRow(getNextAvailableRow() + 1);
		}

		// Getters
		public Label getFretlessLable() {
			return fretlessLable;
		}

		public CheckBox getFretlessCheckBox() {
			return fretlessCheckBox;
		}

		// checks if there are no empty fields
		public void checkIfNoEmptyFields() {
			super.checkIfNoEmptyFields();
		}

		// make new instrument by attributes
		public Bass makeNewInstrumentByAttributes() {
			checkIfNoEmptyFields();
			Double price = convertPriceFromStringToDouble(getPriceTextField().getText());
			Integer numberOfStrings = convertNumberOfStringsFromStringToInteger(
					(getNumberOfStringsTextField().getText()));
			return new Bass(price, getBrandTextField().getText(), numberOfStrings, getFretlessCheckBox().isSelected());
		}
	}

	// inner class - Flute Add Box
	public class FluteAddBox extends MusicalInstrumentAddBox {
		// finals
		final ObservableList<String> MATERIAL = FXCollections.observableList(Arrays.asList("Metal", "Wood", "Plastic"));
		final ObservableList<String> FLUTE_TYPES = FXCollections
				.observableList(Arrays.asList("Transverse", "Bass", "Recorder"));

		// Nodes
		private Label materialLable = new Label("Material:");
		private ComboBox<String> materialCombo = new ComboBox<>(MATERIAL);
		private Label fluteTypeLable = new Label("Flute Type:");
		private ComboBox<String> fluteTypeCombo = new ComboBox<>(FLUTE_TYPES);

		// Constructor
		public FluteAddBox() {
			super();

			// set Nodes
			getBrandTextField().setPromptText("Ex: Levit");
			getPriceTextField().setPromptText("Ex: 300");
			getMaterialCombo().setPromptText("Material");
			getFluteTypeCombo().setPromptText("Type");

			// add rows to grid
			addRow(getNextAvailableRow(), getMaterialLable(), getMaterialCombo());
			setNextAvailableRow(getNextAvailableRow() + 1);
			addRow(getNextAvailableRow(), getFluteTypeLable(), getFluteTypeCombo());
			setNextAvailableRow(getNextAvailableRow() + 1);
		}

		// Getters
		public Label getMaterialLable() {
			return materialLable;
		}

		public ComboBox<String> getMaterialCombo() {
			return materialCombo;
		}

		public Label getFluteTypeLable() {
			return fluteTypeLable;
		}

		public ComboBox<String> getFluteTypeCombo() {
			return fluteTypeCombo;
		}

		// checks if there are no empty fields
		public void checkIfNoEmptyFields() {
			super.checkIfNoEmptyFields();
			if (MATERIAL.indexOf(getMaterialCombo().getValue()) < 0)
				throw new InputMismatchException("You cannot leave empty fields.\r\nPlease choose material");
			if (FLUTE_TYPES.indexOf(getFluteTypeCombo().getValue()) < 0)
				throw new InputMismatchException("You cannot leave empty fields.\r\nPlease choose flute type");
		}

		// make new instrument by attributes
		public Flute makeNewInstrumentByAttributes() {
			checkIfNoEmptyFields();
			Double price = convertPriceFromStringToDouble(getPriceTextField().getText());
			return new Flute(price, getBrandTextField().getText(), getMaterialCombo().getValue(),
					getFluteTypeCombo().getValue());
		}
	}

	// inner class - Saxophone Add Box
	public class SaxophoneAddBox extends MusicalInstrumentAddBox {

		// Constructor
		public SaxophoneAddBox() {
			super();

			// set Nodes
			getBrandTextField().setPromptText("Ex: Jupiter");
			getPriceTextField().setPromptText("Ex: 5490");
		}

		// checks if there are no empty fields
		public void checkIfNoEmptyFields() {
			super.checkIfNoEmptyFields();
		}

		// make new instrument by attributes
		public Saxophone makeNewInstrumentByAttributes() {
			checkIfNoEmptyFields();
			Double price = convertPriceFromStringToDouble(getPriceTextField().getText());
			return new Saxophone(price, getBrandTextField().getText());
		}
	}

}
