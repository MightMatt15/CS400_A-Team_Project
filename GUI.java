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
		
		Label title  = new Label("Milk Production Dashboard\n   -- Chalet Cheese Factory");
		title.setFont(Font.font("Marker Felt", FontWeight.EXTRA_BOLD, 25));
		panel.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		
		VBox selectionPanel = new VBox();
		Label selection = new Label("Filters");
		selection.setFont(Font.font("Chalkduster", FontWeight.BOLD, 20));
		Label year = new Label("Year");
		year.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		TextField yearInput = new TextField();
		Label month = new Label("Month");
		month.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		TextField monthInput = new TextField();
		Label day = new Label("Day");
		day.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		TextField dayInput = new TextField();
		Label farm = new Label("Farm");
		farm.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
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
		
		Scene mainScene = new Scene(panel, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Add the stuff and set the primary stage
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
