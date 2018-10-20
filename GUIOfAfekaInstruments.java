import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class GUIOfAfekaInstruments extends Application {
	// finals
	final int MAIN_WINDOW_HEIGHT = 330, MAIN_WINDOW_MINIMUM_HEIGHT = 290, MAIN_WINDOW_WIDTH = 650,
			MAIN_WINDOW_MINIMUM_WIDTH = 320, MAIN_WINDOW_WITH_ADD_PANE_MINIMUM_WIDTH = 620;
	final double INITIAL_UP_DOWN_DIVIDER_POSITION = 0.9, INITIAL_LEFT_RIGHT_DIVIDER_POSITION = 0.5;

	// variables
	private double upDownSplitPositionSaved = INITIAL_UP_DOWN_DIVIDER_POSITION;
	private double leftRightSplitPositionSaved = INITIAL_LEFT_RIGHT_DIVIDER_POSITION;
	private boolean windowSizeChanged;
	private boolean FilesloadedSuccessfully;
	private boolean leftRightInitializationHappened;
	private boolean upDownInitializationHappened;
	private boolean addBoxPresent;
	private long windowSizeMillTimeChanged = getTimeInMill();

	// Arrays
	private ArrayList<String> namseOfFiles;
	private ArrayList<MusicalInstrument> afekaStore = new ArrayList<>();

	// Panes
	private SplitPane upDownSplit;
	private SplitPane leftRightSplit;
	private ShowInstrumentsPane showInstrumentsPane;
	private AddInstrumentsPane addInstrumentsPane;
	private MovingTextPane movingTextPane;

	// Stage
	private Stage mainWindow;

	// main
	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		setMainWindow(primaryStage);
		setFileChoiceDialog();

		// set Panes
		setMovingTextPane(new MovingTextPane());
		stopAnimationWhenClosing();
		setShowInstrumentsPane(new ShowInstrumentsPane(getAfekaStore()));
		setLeftRightSplit(new SplitPane());
		setUpDownSplit(new SplitPane());

		// add children
		getLeftRightSplit().getItems().add(getShowInstrumentsPane());
		getUpDownSplit().getItems().addAll(getLeftRightSplit(), getMovingTextPane());

		// set listeners
		setVerticalSplitDividerListener(getUpDownSplit());
		setAddButtonOfShowBoxListener();
		setKeyboardListeners();

		// scene and window show
		Scene s = new Scene(getUpDownSplit());
		getMainWindow().setScene(s);
		getMainWindow().show();

		// Initialization of up down divider after window show
		getUpDownSplit().setDividerPositions(INITIAL_UP_DOWN_DIVIDER_POSITION);
		getLeftRightSplit().setDividerPositions(INITIAL_LEFT_RIGHT_DIVIDER_POSITION);

	}

	// Setters and Getters
	public void setMainWindow(Stage window) {
		mainWindow = window;
		mainWindow.setTitle("Afeka Instruments Music Store");
		mainWindow.setAlwaysOnTop(true);
		mainWindow.setHeight(MAIN_WINDOW_HEIGHT);
		mainWindow.setWidth(MAIN_WINDOW_WIDTH);
		mainWindow.setMinHeight(MAIN_WINDOW_MINIMUM_HEIGHT);
		mainWindow.setMinWidth(MAIN_WINDOW_MINIMUM_WIDTH);
	}

	public Stage getMainWindow() {
		return mainWindow;
	}

	private void setUpDownSplit(SplitPane upDownSplit) {
		this.upDownSplit = upDownSplit;
		this.upDownSplit.setOrientation(Orientation.VERTICAL);
	}

	public SplitPane getUpDownSplit() {
		return upDownSplit;
	}

	private void setLeftRightSplit(SplitPane leftRightSplit) {
		this.leftRightSplit = leftRightSplit;
		this.leftRightSplit.setOrientation(Orientation.HORIZONTAL);
	}

	public SplitPane getLeftRightSplit() {
		return leftRightSplit;
	}

	public boolean isLeftRightInitializationHappened() {
		return leftRightInitializationHappened;
	}

	public void setLeftRightInitializationHappened(boolean leftRightInitializationHappened) {
		this.leftRightInitializationHappened = leftRightInitializationHappened;
	}

	public boolean isUpDownInitializationHappened() {
		return upDownInitializationHappened;
	}

	public void setUpDownInitializationHappened(boolean upDownInitializationHappened) {
		this.upDownInitializationHappened = upDownInitializationHappened;
	}

	public boolean isAddBoxPresent() {
		return addBoxPresent;
	}

	public void setAddBoxPresent(boolean addBoxIsPresent) {
		this.addBoxPresent = addBoxIsPresent;
	}

	public ShowInstrumentsPane getShowInstrumentsPane() {
		return showInstrumentsPane;
	}

	public void setShowInstrumentsPane(ShowInstrumentsPane showInstrumentsPane) {
		this.showInstrumentsPane = showInstrumentsPane;
	}

	public MovingTextPane getMovingTextPane() {
		return movingTextPane;
	}

	public void setMovingTextPane(MovingTextPane movingTextPane) {
		this.movingTextPane = movingTextPane;
	}

	public AddInstrumentsPane getAddInstrumentsPane() {
		return addInstrumentsPane;
	}

	public void setAddInstrumentsPane(AddInstrumentsPane addInstrumentsPane) {
		this.addInstrumentsPane = addInstrumentsPane;
	}

	public boolean isWindowSizeChanged() {
		return windowSizeChanged;
	}

	public void setWindowSizeChanged(boolean windowSizeChanged) {
		this.windowSizeChanged = windowSizeChanged;
	}

	public double getUpDownSplitPositionSaved() {
		return upDownSplitPositionSaved;
	}

	public void setUpDownSplitPositionSaved(double upDownSplitPosition) {
		this.upDownSplitPositionSaved = upDownSplitPosition;
	}

	public double getLeftRightSplitPositionSaved() {
		return leftRightSplitPositionSaved;
	}

	public void setLeftRightSplitPositionSaved(double leftRightSplitPositionSaved) {
		this.leftRightSplitPositionSaved = leftRightSplitPositionSaved;
	}

	public long getWindowSizeMillTimeChanged() {
		return windowSizeMillTimeChanged;
	}

	public void setWindowSizeMillTimeChanged(long windowSizeMillTimeChanged) {
		this.windowSizeMillTimeChanged = windowSizeMillTimeChanged;
	}

	public boolean isFilesloadedSuccessfully() {
		return FilesloadedSuccessfully;
	}

	public void setFilesloadedSuccessfully(boolean filesloadedSuccessfully) {
		FilesloadedSuccessfully = filesloadedSuccessfully;
	}

	public ArrayList<String> getNamesOfFiles() {
		return namseOfFiles;
	}

	public void setNamesOfFiles(ArrayList<String> namesOfFiles) {
		this.namseOfFiles = namesOfFiles;
	}

	public ArrayList<MusicalInstrument> getAfekaStore() {
		return afekaStore;
	}

	// set and pop the File Choice dialog
	private void setFileChoiceDialog() throws IOException {
		boolean userWantsToContinue;
		setNamesOfFiles(new ArrayList<String>());
		readFilesFromDirectory(getNamesOfFiles());
		do {
			userWantsToContinue = true;
			ChoiceDialog<String> fileChoiceDialog = new ChoiceDialog<>("Choose your file:", getNamesOfFiles());
			fileChoiceDialog.setTitle("Confirmation");
			fileChoiceDialog.setHeaderText("Load Instruments From File");
			fileChoiceDialog.setContentText("Please choose a file:");
			((Stage) fileChoiceDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
			Optional<String> result = fileChoiceDialog.showAndWait();

			userWantsToContinue = result.isPresent();
			result.ifPresent(fileName -> {
				try {
					AfekaInstruments.loadInstrumentsFromFile(new Scanner(new File(fileName)), getAfekaStore());
					setFilesloadedSuccessfully(true);
				} catch (FileNotFoundException e) {
					popErrorDialog("File not found, please choose another file");
				} catch (NoSuchElementException | IllegalArgumentException ex) {
					popErrorDialog(ex.getMessage());
				}
			});
		} while (!isFilesloadedSuccessfully() && userWantsToContinue);

		if (!userWantsToContinue)
			System.exit(0);

	}

	// set vertical divider listener
	private void setVerticalSplitDividerListener(SplitPane upDownSplit) {
		upDownSplit.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
			setWindowSizeChanged(false);
			if (!isUpDownInitializationHappened()) {
				getMainWindow().heightProperty().addListener((obse, oldValu, newValu) -> {
					setWindowSizeChanged(true);
					upDownSplit.setDividerPositions(getUpDownSplitPositionSaved());
					setWindowSizeMillTimeChanged(getTimeInMill());
					// System.out.println("1 -> " + getUpDownSplitPositionSaved());
				});

				getMainWindow().maximizedProperty().addListener((obser, oldValue, newValue) -> {
					setWindowSizeChanged(true);
					upDownSplit.setDividerPositions(1);
					setWindowSizeMillTimeChanged(getTimeInMill());
					// System.out.println("2 -> " + getUpDownSplitPositionSaved());
				});
				setUpDownInitializationHappened(true);
			}

			if (!isWindowSizeChanged() && getTimeInMill() - getWindowSizeMillTimeChanged() > 500) {
				setUpDownSplitPositionSaved(upDownSplit.getDividerPositions()[0]);
				upDownSplit.setDividerPositions(getUpDownSplitPositionSaved());
				// System.out.println("3 -> " + getUpDownSplitPositionSaved() + " -> " +
				// (getTimeInMill() - getWindowSizeMillTimeChanged()));
			}
		});

		getMainWindow().maximizedProperty().addListener((obser, oldValue, newValue) -> {
			upDownSplit.setDividerPositions(getUpDownSplitPositionSaved());
			setWindowSizeMillTimeChanged(getTimeInMill());
			// System.out.println("4 -> " + getUpDownSplitPositionSaved());
		});
	}

	// set horizontal divider listener
	private void setHorizontalSplitDividerListener(SplitPane leftRightSplit) {
		leftRightSplit.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
			setWindowSizeChanged(false);
			if (!isLeftRightInitializationHappened()) {
				getMainWindow().widthProperty().addListener((obse, oldValu, newValu) -> {
					setWindowSizeChanged(true);
					leftRightSplit.setDividerPositions(getLeftRightSplitPositionSaved());
					setWindowSizeMillTimeChanged(getTimeInMill());
					// System.out.println("1 -> " + getLeftRightSplitPositionSaved());
				});

				getMainWindow().maximizedProperty().addListener((obser, oldValue, newValue) -> {
					setWindowSizeChanged(true);
					leftRightSplit.setDividerPositions(1);
					setWindowSizeMillTimeChanged(getTimeInMill());
					// System.out.println("2 -> " + getLeftRightSplitPositionSaved());
				});
				setLeftRightInitializationHappened(true);
			}

			if (!isWindowSizeChanged() && getTimeInMill() - getWindowSizeMillTimeChanged() > 500) {
				setLeftRightSplitPositionSaved(leftRightSplit.getDividerPositions()[0]);
				leftRightSplit.setDividerPositions(getLeftRightSplitPositionSaved());
				// System.out.println("3 -> " + getLeftRightSplitPositionSaved() + " -> "
				// + (getTimeInMill() - getWindowSizeMillTimeChanged()));
			}
		});

		getMainWindow().maximizedProperty().addListener((obser, oldValue, newValue) -> {
			leftRightSplit.setDividerPositions(getLeftRightSplitPositionSaved());
			setWindowSizeMillTimeChanged(getTimeInMill());
			// System.out.println("4 -> " + getLeftRightSplitPositionSaved());
		});
	}

	// set keyboard listeners
	private void setKeyboardListeners() {
		getShowInstrumentsPane().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.RIGHT && !getShowInstrumentsPane().getSearchBox().getSearchBar().isFocused()) {
				getShowInstrumentsPane().getRightButton().fire();
				getShowInstrumentsPane().getRightButton().requestFocus();
				e.consume();
			}
			if (e.getCode() == KeyCode.LEFT && !getShowInstrumentsPane().getSearchBox().getSearchBar().isFocused()) {
				getShowInstrumentsPane().getLeftButton().fire();
				getShowInstrumentsPane().getLeftButton().requestFocus();
				e.consume();
			}
			if (e.getCode() == KeyCode.ENTER) {
				getShowInstrumentsPane().getSearchBox().getGoButton().fire();
				getShowInstrumentsPane().getSearchBox().getGoButton().requestFocus();

			}
			if (e.getCode() == KeyCode.DELETE && !getShowInstrumentsPane().getSearchBox().getSearchBar().isFocused()) {
				getShowInstrumentsPane().getButtonBox().getDeleteButton().fire();
				getShowInstrumentsPane().getButtonBox().getDeleteButton().requestFocus();
			}
			if (e.getCode() == KeyCode.A && !getShowInstrumentsPane().getSearchBox().getSearchBar().isFocused()) {
				getShowInstrumentsPane().getButtonBox().getAddButton().fire();
				getShowInstrumentsPane().getButtonBox().requestFocus();
			}
		});
	}

	// set add button of Show Box listener
	private void setAddButtonOfShowBoxListener() {
		getShowInstrumentsPane().getButtonBox().getAddButton().setOnAction(e -> {
			if (!isAddBoxPresent()) {
				setAddBoxPresent(true);
				// set pane
				setAddInstrumentsPane(new AddInstrumentsPane());
				getLeftRightSplit().getItems().add(getAddInstrumentsPane());
				getShowInstrumentsPane().getButtonBox().getAddButton().setVisible(false);
				getMainWindow().setMinWidth(MAIN_WINDOW_WITH_ADD_PANE_MINIMUM_WIDTH);

				// set cancel and add buttons listeners
				setCancelButtonOfAddPaneListener();
				setAddButtonOfAddPanelistener();

				// set left right divider listener
				setHorizontalSplitDividerListener(getLeftRightSplit());

				// Initialization of left right divider after window show
				getLeftRightSplit().setDividerPositions(INITIAL_LEFT_RIGHT_DIVIDER_POSITION);
			}

		});
	}

	// set cancel button of Add Pane listener
	private void setCancelButtonOfAddPaneListener() {
		getAddInstrumentsPane().getCancelButton().setOnAction(e -> {
			getLeftRightSplit().getItems().remove(getAddInstrumentsPane());
			getShowInstrumentsPane().getButtonBox().getAddButton().setVisible(true);
			getMainWindow().setMinWidth(MAIN_WINDOW_MINIMUM_WIDTH);
			setAddBoxPresent(false);
		});
	}

	// set add button of Add Pane listener
	private void setAddButtonOfAddPanelistener() {
		getAddInstrumentsPane().getAddButton().setOnAction(e -> {
			try {
				MusicalInstrument newMusicalInstrument = getAddInstrumentsPane().getMusicalInstrumentAddBox()
						.makeNewInstrumentByAttributes();
				getAfekaStore().add(newMusicalInstrument);
				getShowInstrumentsPane().setArrayToShow(getAfekaStore(), getAfekaStore().indexOf(newMusicalInstrument));
				getShowInstrumentsPane().getSearchBox().getSearchBar().clear();
			} catch (Exception ex) {
				popErrorDialog(ex.getMessage());
			}
		});
	}

	// stop animation when closing
	private void stopAnimationWhenClosing() {
		getMainWindow().setOnCloseRequest(e -> {
			getMovingTextPane().getSideToSide().stop();
			getMovingTextPane().getClock().stop();
		});
	}

	// read files from project directory
	public void readFilesFromDirectory(ArrayList<String> nameOfFiles) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(""))) {
			paths.filter(Files::isRegularFile).forEach(somePath -> {
				if (somePath.toString().contains(".txt"))
					nameOfFiles.add(somePath.toString());
			});
		}
	}

	// pop Error dialog
	public void popErrorDialog(String errorString) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error");
		alert.setContentText(errorString);
		((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
		alert.showAndWait();
	}

	// get the time since 1970 in milliseconds
	public long getTimeInMill() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

}
