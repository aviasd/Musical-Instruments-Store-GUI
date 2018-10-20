import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MovingTextPane extends BorderPane {
	// finals
	final String SALES_INFO = "Afeka instrument Music Store $$$ ON SALE!!! $$$ Guitars, Basses, Flutes, Saxophones and more!";
	final int FONT_SIZE = 12, DURATION_OF_MOVEMENT = 10;
	final Insets PADDING = new Insets(10, 0, 0, 0);

	// variables
	private Text movingText;

	// Animations
	private Timeline clock;
	private Timeline sideToSide;

	// Pane
	ScrollPane scrollPane = new ScrollPane();

	// Constructor
	public MovingTextPane() {
		setMovingText(new Text());
		setAnimation();

		// set scroll pane
		getScrollPane().setContent(getMovingText());
		getScrollPane().setHbarPolicy(ScrollBarPolicy.NEVER);
		getScrollPane().setVbarPolicy(ScrollBarPolicy.NEVER);
		getScrollPane().setMaxHeight(0);
		getScrollPane().setStyle(
				"-fx-background-color: -fx-control-inner-background; -fx-background-insets: 0; -fx-padding: 0;");
		setCenter(getScrollPane());
		setPadding(PADDING);
	}

	// Getters and Setters
	public Text getMovingText() {
		return movingText;
	}

	public void setMovingText(Text movingText) {
		this.movingText = movingText;
		this.movingText.setText(getTimeAndDate() + SALES_INFO);
		this.movingText.setFill(javafx.scene.paint.Color.RED);
		this.movingText.setFont(Font.font(null, FontWeight.BOLD, FONT_SIZE));
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setClock(Timeline clock) {
		this.clock = clock;
	}

	public Timeline getClock() {
		return clock;
	}

	public void setSideToSide(Timeline sideToSide) {
		this.sideToSide = sideToSide;
	}

	public Timeline getSideToSide() {
		return sideToSide;
	}

	// set animation of text
	private void setAnimation() {
		// The end property of the side to side movement
		KeyValue endKeyValue = new KeyValue(getMovingText().translateXProperty(),
				0.25 * getMovingText().getLayoutBounds().getWidth());
		// The end frame of side to side movement
		KeyFrame endFrame = new KeyFrame(Duration.seconds(DURATION_OF_MOVEMENT), endKeyValue);

		// side to side movement
		setSideToSide(new Timeline(endFrame));
		getSideToSide().setCycleCount(Timeline.INDEFINITE);
		getSideToSide().setAutoReverse(true);
		getSideToSide().play();

		// clock
		setClock(new Timeline(
				(new KeyFrame(Duration.seconds(1), e -> getMovingText().setText(getTimeAndDate() + SALES_INFO)))));
		getClock().setCycleCount(Timeline.INDEFINITE);
		getClock().play();

		// pause affect
		getMovingText().setOnMouseMoved(e -> getSideToSide().pause());
		getMovingText().setOnMouseExited(e -> getSideToSide().play());
		
		// set smooth movement
		getMovingText().setCache(true);
		getMovingText().setCacheHint(CacheHint.SPEED);
	}

	// gets the updated time and date
	public String getTimeAndDate() {
		String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return date + " " + time + " ";
	}

}
