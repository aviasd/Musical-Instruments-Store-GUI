import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ShowInstrumentsPane extends BorderPane {
	// finals
	final Insets PADDING = new Insets(10, 10, 10, 10);

	// variables
	private int index;

	// Nodes
	private ButtonsBox buttonBox = new ButtonsBox();
	private SearchBox searchBox = new SearchBox();
	private ShowBox showBox = new ShowBox();
	private Button leftButton = new Button("<");
	private Button rightButton = new Button(">");

	// ArrayList
	private ArrayList<MusicalInstrument> originalArray;
	private ArrayList<MusicalInstrument> arrayToShow;

	// Constructor
	public ShowInstrumentsPane(ArrayList<MusicalInstrument> originalArray) {
		setOriginalArray(originalArray);
		setArrayToShow(getOriginalArray());
		setChildren();

		// set listeners
		setArrowButtonsListeners();
		setClearAndDeleteButtonsListeners();
		setGoButtonListener();

	}

	// Getters and Setters
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public SearchBox getSearchBox() {
		return searchBox;
	}

	public ArrayList<MusicalInstrument> getOriginalArray() {
		return originalArray;
	}

	public void setOriginalArray(ArrayList<MusicalInstrument> originalArray) {
		this.originalArray = originalArray;
	}

	public ArrayList<MusicalInstrument> getArrayToShow() {
		return arrayToShow;
	}

	public void setArrayToShow(ArrayList<MusicalInstrument> arrayToShow) {
		setArrayToShow(arrayToShow, 0);
	}

	public void setArrayToShow(ArrayList<MusicalInstrument> arrayToShow, int index) {
		this.arrayToShow = new ArrayList<>(arrayToShow);
		getShowBox().clearShow();
		setIndex(index);
		showInstrumentByIndex(getIndex());
	}

	public ShowBox getShowBox() {
		return showBox;
	}

	public Button getLeftButton() {
		return leftButton;
	}

	public Button getRightButton() {
		return rightButton;
	}

	public ButtonsBox getButtonBox() {
		return buttonBox;
	}

	private void setChildren() {
		// set alignment of children
		BorderPane.setAlignment(getButtonBox(), Pos.CENTER);
		BorderPane.setAlignment(getSearchBox(), Pos.CENTER);
		BorderPane.setAlignment(getShowBox(), Pos.CENTER);
		BorderPane.setAlignment(getLeftButton(), Pos.CENTER);
		BorderPane.setAlignment(getRightButton(), Pos.CENTER);

		// set position of children
		setBottom(getButtonBox());
		setTop(getSearchBox());
		setCenter(getShowBox());
		setLeft(getLeftButton());
		setRight(getRightButton());

		// set buttons
		setMargin(getLeftButton(), PADDING);
		setMargin(getRightButton(), PADDING);
	}

	// arrow buttons listeners
	private void setArrowButtonsListeners() {
		// left button listener
		getLeftButton().setOnAction(e -> {
			if (getArrayToShow().size() == 0)
				return;
			if (getIndex() <= 0)
				setIndex(getArrayToShow().size() - 1);
			else
				setIndex(getIndex() - 1);
			showInstrumentByIndex(getIndex());
		});

		// right button listener
		getRightButton().setOnAction(e -> {
			if (getArrayToShow().size() == 0)
				return;
			if (getIndex() >= getArrayToShow().size() - 1)
				setIndex(0);
			else
				setIndex(getIndex() + 1);
			showInstrumentByIndex(getIndex());
		});
	}

	// clear and delete buttons listeners
	private void setClearAndDeleteButtonsListeners() {
		// clear button listener
		getButtonBox().getClearButton().setOnAction(e -> {
			if (getArrayToShow().size() == 0)
				return;
			getOriginalArray().removeAll(getArrayToShow());
			getArrayToShow().clear();
			getShowBox().clearShow();
			setIndex(0);
		});

		// delete button listener
		getButtonBox().getDeleteButton().setOnAction(e -> {
			if (getArrayToShow().size() == 0)
				return;
			MusicalInstrument deletedInstrument = getArrayToShow().get(getIndex());
			getArrayToShow().remove(deletedInstrument);
			getOriginalArray().remove(deletedInstrument);
			if (getArrayToShow().size() == 0) {
				getShowBox().clearShow();
				setIndex(0);
			} else
				setIndex(getIndex() - 1);
			getRightButton().fire();
		});
	}

	// go button listener
	private void setGoButtonListener() {
		getSearchBox().getGoButton().setOnAction(e -> {
			if (getOriginalArray().size() == 0)
				return;
			String searchString = getSearchBox().getSearchBar().getText().toLowerCase();
			if (searchString.equals("") || searchString == null)
				setArrayToShow(getOriginalArray());
			else {
				ArrayList<MusicalInstrument> searchArray = new ArrayList<>();
				for (MusicalInstrument i : getOriginalArray())
					if (i.toString().toLowerCase().contains(searchString))
						searchArray.add(i);
				setArrayToShow(searchArray);
			}
		});
	}

	// show instrument by index
	public void showInstrumentByIndex(int index) {
		if (getArrayToShow().size() == 0)
			return;
		getShowBox().showInstrument(getArrayToShow().get(index));
	}

	// inner class - Search Box
	public class SearchBox extends HBox {
		// finals
		final Insets PADDING = new Insets(10, 10, 10, 10);
		final int SPACING = 10;

		// Nodes
		private Button goButton = new Button("Go!");
		private TextField searchBar = new TextField();

		// Constructor
		public SearchBox() {
			getChildren().addAll(getSearchBar(), getGoButton());
			getSearchBar().setPromptText("Search...");
			setPadding(PADDING);
			setHgrow(getSearchBar(), Priority.ALWAYS);
			setSpacing(SPACING);
		}

		// Getters
		public Button getGoButton() {
			return goButton;
		}

		public TextField getSearchBar() {
			return searchBar;
		}
	}

	// inner class - Show Box
	public class ShowBox extends GridPane {
		// finals
		final Insets PADDING = new Insets(10, 10, 10, 10);
		final int VERTICAL_GAP = 8, HORIZONTAL_GAP = 10;

		// Text Fields
		private TextField typeTextField = new TextField();
		private TextField brandTextField = new TextField();
		private TextField priceTextField = new TextField();

		// Labels
		private Label typeLabel = new Label("Type:");
		private Label brandLabel = new Label("Brand:");
		private Label priceLabel = new Label("Price:");

		// Constructor
		public ShowBox() {
			setNodes();
			setPadding(PADDING);
			setVgap(VERTICAL_GAP);
			setHgap(HORIZONTAL_GAP);
			setAlignment(Pos.CENTER);
			getChildren().addAll(typeLabel, typeTextField, brandLabel, brandTextField, priceLabel, priceTextField);
		}

		public void showInstrument(MusicalInstrument musicalInstrument) {
			getTypeTextField().setText(musicalInstrument.getClass().getCanonicalName());
			getBrandTextField().setText(musicalInstrument.getBrand());
			getPriceTextField().setText(musicalInstrument.getPrice().doubleValue() + "");
		}

		public void clearShow() {
			getTypeTextField().clear();
			getBrandTextField().clear();
			getPriceTextField().clear();
		}

		// Getters
		public TextField getTypeTextField() {
			return typeTextField;
		}

		public TextField getBrandTextField() {
			return brandTextField;
		}

		public TextField getPriceTextField() {
			return priceTextField;
		}

		public Label getTypeLabel() {
			return typeLabel;
		}

		public Label getBrandLabel() {
			return brandLabel;
		}

		public Label getPriceLabel() {
			return priceLabel;
		}

		private void setNodes() {
			// set constraints of nodes
			GridPane.setConstraints(getTypeLabel(), 0, 0);
			GridPane.setConstraints(getTypeTextField(), 1, 0);
			GridPane.setConstraints(getBrandLabel(), 0, 1);
			GridPane.setConstraints(getBrandTextField(), 1, 1);
			GridPane.setConstraints(getPriceLabel(), 0, 2);
			GridPane.setConstraints(getPriceTextField(), 1, 2);

			// set editable attribute of text fields
			getTypeTextField().setEditable(false);
			getBrandTextField().setEditable(false);
			getPriceTextField().setEditable(false);

			// set prompt text of text fields
			getTypeTextField().setPromptText("No items");
			getBrandTextField().setPromptText("No items");
			getPriceTextField().setPromptText("No items");

		}
	}

	// inner class - Button Box
	public class ButtonsBox extends HBox {
		// finals
		final Insets PADDING = new Insets(10, 10, 10, 10);
		final int SPACING = 20;

		// Buttons
		private Button addButton = new Button("Add");
		private Button deleteButton = new Button("Delete");
		private Button clearButton = new Button("Clear");

		// Constructor
		public ButtonsBox() {
			getChildren().addAll(getAddButton(), getDeleteButton(), getClearButton());
			setPadding(PADDING);
			setSpacing(SPACING);
			setAlignment(Pos.CENTER);
			getAddButton().managedProperty().bind(getAddButton().visibleProperty());
		}

		// Getters
		public Button getAddButton() {
			return addButton;
		}

		public Button getDeleteButton() {
			return deleteButton;
		}

		public Button getClearButton() {
			return clearButton;
		}
	}

}
