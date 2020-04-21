package application;

import java.io.FileInputStream;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 200;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		// args = this.getParameters().getRaw();

		BorderPane panel = new BorderPane();

		Label title = new Label("Milk Production Dashboard\n   -- Chalet Cheese Factory");
		title.setFont(Font.font("Marker Felt", FontWeight.EXTRA_BOLD, 25));
		panel.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		VBox selectionPanel = new VBox();
		Label selection = createLabel("Filters", "Chalkduster", FontWeight.BOLD, 20);
		Label year = createLabel("Year", "Times New Roman", FontWeight.BOLD, 15);
		TextField yearInput = new TextField();
		Label month = createLabel("Month", "Times New Roman", FontWeight.BOLD, 15);
		TextField monthInput = new TextField();
		Label day = createLabel("Day", "Times New Roman", FontWeight.BOLD, 15);
		TextField dayInput = new TextField();
		Label farm = createLabel("Farm", "Times New Roman", FontWeight.BOLD, 15);
		TextField farmInput = new TextField();

		Button search = new Button("SEARCH");

		selectionPanel.getChildren().add(selection);
		selectionPanel.getChildren().add(year);
		selectionPanel.getChildren().add(yearInput);
		selectionPanel.getChildren().add(month);
		selectionPanel.getChildren().add(monthInput);
		selectionPanel.getChildren().add(day);
		selectionPanel.getChildren().add(dayInput);
		selectionPanel.getChildren().add(farm);
		selectionPanel.getChildren().add(farmInput);
		selectionPanel.getChildren().add(search);
		panel.setLeft(selectionPanel);

		VBox resultsPanel = new VBox();
		GridPane results = new GridPane();
		results.add(createLabel("Total:	", "Times New Roman", FontWeight.BOLD, 15), 0, 0);
		results.add(createLabel("Maximum:	", "Times New Roman", FontWeight.BOLD, 15), 0,
				1);
		results.add(createLabel("Minimum:	", "Times New Roman", FontWeight.BOLD, 15), 0,
				2);
		results.add(createLabel("Average:	", "Times New Roman", FontWeight.BOLD, 15), 0,
				3);
		results.add(
				createLabel("Percentage:	", "Times New Roman", FontWeight.BOLD, 15), 0,
				4);
		for (int i = 0; i < 6; i++)
			results.add(new TextField(), 1, i);
		results.add(new TextField("e.g: farm 01"), 2, 2);
		results.add(new TextField("e.g: farm 02"), 2, 3);
		results.add(new Label("(out of all farms)"), 2, 4);
		results.add(new Label("(out of the whole time period)"), 2, 5);

		resultsPanel.getChildren()
				.add(createLabel("Results", "Chalkduster", FontWeight.BOLD, 20));
		resultsPanel.getChildren().add(results);
		results.setAlignment(Pos.TOP_LEFT);
		panel.setCenter(resultsPanel);

		Scene mainScene = new Scene(panel, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Add the stuff and set the primary stage
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * Create a label with specified font, font weight and size.
	 * 
	 * @param label  content of the label
	 * @param font   font
	 * @param weight font weight
	 * @param size   size of font
	 * @return a Label reference
	 */
	private Label createLabel(String label, String font, FontWeight weight, int size) {
		Label newLabel = new Label(label);
		newLabel.setFont(Font.font(font, weight, size));
		return newLabel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
