package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	// manage data provided in files for visualization and manipulation
	DataManager dataManager = new DataManager();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		// args = this.getParameters().getRaw();

		// dashboard
		BorderPane panel = new BorderPane();

		// change the background color
		Background background = new Background(
				new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));
		panel.setBackground(background);

		// title on the dashboard
		Label title = new Label("Milk Production Dashboard\n   -- Chalet Cheese Factory");
		title.setFont(Font.font("Marker Felt", FontWeight.EXTRA_BOLD, 25));
		panel.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

		// create the input boxes on the left
		VBox selectionPanel = new VBox();
		Label selection = createLabel("Filters", "Chalkduster", FontWeight.BOLD, 20);
		Label year = createLabel("Year", "Times New Roman", FontWeight.BOLD, 15);
		TextField yearInput = new TextField("Enter Year:");
		Label month = createLabel("Month", "Times New Roman", FontWeight.BOLD, 15);
		TextField monthInput = new TextField("Enter Month Number:");
		Label day = createLabel("Day", "Times New Roman", FontWeight.BOLD, 15);
		TextField dayInput = new TextField("Enter Day Number:");
		Label farm = createLabel("Farm", "Times New Roman", FontWeight.BOLD, 15);
		TextField farmInput = new TextField("Enter farm ID:");

		// new button to search
		Button search = new Button("SEARCH");

		// add the text fields to the V box on the left
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

		search.setOnAction(event -> {// show weight for that day?
			try {
				int weight = dataManager.getSingleData(farmInput.getText(),
						Integer.parseInt(yearInput.getText()),
						Integer.parseInt(monthInput.getText()),
						Integer.parseInt(dayInput.getText()));
				Alert alert;
				if (weight == -2) {
					alert = new Alert(AlertType.ERROR,
							"The farmID entered does not exist in the data");
					alert.showAndWait();
				} else if (weight == -1) {
					alert = new Alert(AlertType.ERROR,
							"The year entered does not exist in the data");
					alert.showAndWait();
				} else {
					Label searchedData = createLabel("Farm " + farmInput.getText()
							+ "'s data for that day: " + weight, "Times New Roman",
							FontWeight.BOLD, 15);
					selectionPanel.getChildren().remove(searchedData);
					selectionPanel.getChildren().add(searchedData);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				Alert alert = new Alert(AlertType.ERROR,
						"The day or month entered does not exist in the data");
				alert.showAndWait();
			}
		});

		// add a section for showing total, max, min, avg, and percentages
		VBox resultsPanel = new VBox();
		GridPane results = new GridPane();
		// results.add(createLabel("Total: ", "Times New Roman", FontWeight.BOLD, 15),
		// 0, 0);
		results.add(createLabel("Maximum By Month:  ", "Times New Roman", FontWeight.BOLD,
				15), 0, 0);
		results.add(createLabel("Maximum All Farms: ", "Times New Roman", FontWeight.BOLD,
				15), 0, 1);
		results.add(createLabel("Minimum By Month:  ", "Times New Roman", FontWeight.BOLD,
				15), 0, 2);
		results.add(createLabel("Minimum All Farms:    ", "Times New Roman",
				FontWeight.BOLD, 15), 0, 3);
		results.add(createLabel("Average By Month:  ", "Times New Roman", FontWeight.BOLD,
				15), 0, 4);
		results.add(createLabel("Average All Farms: ", "Times New Roman", FontWeight.BOLD,
				15), 0, 5);
		results.add(
				createLabel("Percentage:    ", "Times New Roman", FontWeight.BOLD, 15), 0,
				6);
		results.add(createLabel("Percentage For Month:", "Times New Roman",
				FontWeight.BOLD, 15), 0, 7);
		results.add(createLabel("Percentage For Year:", "Times New Roman",
				FontWeight.BOLD, 15), 0, 8);

		TextField maxMonthTF = new TextField("Enter farmID,year:");
		TextField maxAllFarmsTF = new TextField("Enter month,year:");
		TextField minMonthTF = new TextField("Enter farmID,year:");
		TextField minAllFarmsTF = new TextField("Enter month,year:");
		TextField avgMonthTF = new TextField("Enter farmID,year:");
		TextField avgAllFarmsTF = new TextField("Enter month,year:");
		TextField pctgMonth = new TextField("Enter Month:");
		TextField pctgYear = new TextField("Enter Year:");
		Button pctgTotal = new Button("Farms' Percentage of Total");

		results.add(maxMonthTF, 1, 0);
		results.add(maxAllFarmsTF, 1, 1);
		results.add(minMonthTF, 1, 2);
		results.add(minAllFarmsTF, 1, 3);
		results.add(avgMonthTF, 1, 4);
		results.add(avgAllFarmsTF, 1, 5);
		results.add(pctgMonth, 1, 6);
		results.add(pctgYear, 1, 7);
		results.add(pctgTotal, 1, 8);

		resultsPanel.getChildren()
				.add(createLabel("Results", "Chalkduster", FontWeight.BOLD, 20));
		resultsPanel.getChildren().add(results);
		results.setAlignment(Pos.TOP_LEFT);
		panel.setCenter(resultsPanel);

		// results screen
		BorderPane panelResults = new BorderPane();

		Scene resultsScene = new Scene(panelResults, WINDOW_WIDTH, WINDOW_HEIGHT);

		// create the boxes to hold the buttons in each corner on the bottom of the
		// dashboard
		HBox bottomBox = new HBox();
		HBox leftBox = new HBox();
		HBox.setHgrow(leftBox, Priority.ALWAYS);
		HBox rightBox = new HBox();
		HBox.setHgrow(rightBox, Priority.ALWAYS);

		// input choice screen
		Button inputFile = new Button("Input a file");
		BorderPane.setAlignment(inputFile, Pos.BOTTOM_LEFT);

		// button to go to next scene
		Button nextScene = new Button("Output a file");
		BorderPane.setAlignment(nextScene, Pos.BOTTOM_RIGHT);

		// add the boxes with the buttons
		leftBox.getChildren().add(inputFile);
		rightBox.getChildren().add(nextScene);
		leftBox.setAlignment(Pos.CENTER_LEFT);
		rightBox.setAlignment(Pos.CENTER_RIGHT);
		bottomBox.getChildren().addAll(leftBox, rightBox);
		panel.setBottom(bottomBox);

		// set the main scene
		Scene mainScene = new Scene(panel, WINDOW_WIDTH, WINDOW_HEIGHT);

		// secondary scene

		// output file scene
		BorderPane panel2 = new BorderPane();

		// input file scene
		BorderPane panel3 = new BorderPane();
		Background background3 = new Background(
				new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));
		panel3.setBackground(background3);
		Label title3 = new Label("Milk Production Input\n  -- Chalet Cheese Factory");
		title3.setFont(Font.font("Marker Felt", FontWeight.EXTRA_BOLD, 25));
		panel3.setTop(title3);
		BorderPane.setAlignment(title3, Pos.CENTER);

		// change the background color
		Background background2 = new Background(
				new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));
		panel2.setBackground(background2);

		// title on the dashboard
		Label title2 = new Label("Milk Production Output\n   -- Chalet Cheese Factory");
		title2.setFont(Font.font("Marker Felt", FontWeight.EXTRA_BOLD, 25));
		panel2.setTop(title2);
		BorderPane.setAlignment(title2, Pos.CENTER);

		// button to go to dashboard
		Button dashboard = new Button("Go to Dashboard");
		panel2.setRight(dashboard);
		BorderPane.setAlignment(dashboard, Pos.BOTTOM_RIGHT);

		// button to go back to dashboard from the input scene
		Button dashboard1 = new Button("Go to Dashboard");
		panel3.setRight(dashboard1);
		BorderPane.setAlignment(dashboard1, Pos.BOTTOM_RIGHT);
		VBox input = new VBox();
		input.getChildren().add(createLabel("Please input file location Below",
				"Chalkduster", FontWeight.BOLD, 20));
		TextField inputTextField = new TextField("Ex C:/users/myUser/file.txt");
		input.getChildren().add(inputTextField);
		panel3.setCenter(input);
		BorderPane.setAlignment(panel3, Pos.CENTER);

		// create input fields to output files
		VBox outputTypes = new VBox();
		outputTypes.getChildren()
				.add(createLabel("Output Types", "Chalkduster", FontWeight.BOLD, 20));
		outputTypes.getChildren()
				.add(createLabel("Farm Report", "Times New Roman", FontWeight.BOLD, 15));
		outputTypes.getChildren().add(new TextField("Farm ID, Year   e.g. 02, 2019"));
		outputTypes.getChildren().add(
				createLabel("Annual Report", "Times New Roman", FontWeight.BOLD, 15));
		outputTypes.getChildren().add(new TextField("Year   e.g. 2019"));
		outputTypes.getChildren().add(
				createLabel("Monthly Report", "Times New Roman", FontWeight.BOLD, 15));
		outputTypes.getChildren().add(new TextField("Year, Month   e.g. 2019, 2"));
		outputTypes.getChildren().add(
				createLabel("Date Range Report", "Times New Roman", FontWeight.BOLD, 15));
		outputTypes.getChildren()
				.add(new TextField("Start date, End Date:   year,month,day, month,day"));
		panel2.setCenter(outputTypes);
		BorderPane.setAlignment(panel2, Pos.CENTER);

		// page for outputs
		Scene secondaryScene = new Scene(panel2, WINDOW_WIDTH, WINDOW_HEIGHT);
		// page for inputs
		Scene inputScene = new Scene(panel3, WINDOW_WIDTH, WINDOW_HEIGHT);
		// make the buttons switch between scenes
		nextScene.setOnAction(e -> {
			primaryStage.setScene(secondaryScene);
			primaryStage.show();
		});
		dashboard.setOnAction(e -> {
			primaryStage.setScene(mainScene);
			primaryStage.show();
		});
		inputFile.setOnAction(e -> {
			primaryStage.setScene(inputScene);
			primaryStage.show();
		});
		dashboard1.setOnAction(e -> {
			primaryStage.setScene(mainScene);
			primaryStage.show();
		});

		// read the input file when a user types in a filename
		inputTextField.setOnAction(e -> {
			try {
				if (dataManager.readFile(inputTextField.getText())) {
					Alert alert = new Alert(AlertType.INFORMATION,
							"Input file "
									+ inputTextField.getText().substring(
											inputTextField.getText().lastIndexOf('\\'))
									+ " loaded");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR,
							"Input file was unable to be loaded");
					alert.showAndWait();
				}
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR,
						"Input file was unable to be loaded");
				alert.showAndWait();
			}
		});

		
		
		
        VBox resultsVBox = new VBox();

		
		//results
        maxMonthTF.setOnAction(event -> {
          //page for results
          try {
            //get inputs from the text field
            String[] inputResults = maxMonthTF.getText().split(",");
            //clear the screen and print the results
            resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
            resultsVBox.getChildren().add(createLabel("Results for Maximum Weights for " + maxMonthTF.getText(), "Chalkduster", FontWeight.BOLD, 20));
            resultsVBox.getChildren().add(createLabel("January Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("February Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("March Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("April Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("May Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("June Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("July Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("August Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("September Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("October Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("November Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("December Max: " + dataManager.getMonthlyMax(), "Times New Roman", FontWeight.BOLD, 15));
            panelResults.setCenter(resultsVBox);
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          }catch(ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"farmID,year\"");
            alert.showAndWait();
          }
          });
        
        maxAllFarmsTF.setOnAction(event -> {
        //page for results
          try {
            //get inputs from the text field
            String[] inputResults = maxAllFarmsTF.getText().split(",");
            //clear the screen and print the results
            resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
            resultsVBox.getChildren().add(createLabel("Results for Maximum Weights for " + maxAllFarmsTF.getText(), "Chalkduster", FontWeight.BOLD, 20));
           
            //get all of the farms and print out their maximums for the month
            ArrayList<Farm> farms = cheeseFactory.getFarmList();
            for(int i = 0; i < farms.size(); ++i)
              resultsVBox.getChildren().add(createLabel("Farm: " + farms.get(i).getFarmID() + ": "+ dataManager.getMonthlyMaxForFarm(), "Times New Roman", FontWeight.BOLD, 15));
            
            //change the scene to the results scene
            panelResults.setCenter(resultsVBox);
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          }catch(ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"farmID,year\"");
            alert.showAndWait();
          }
        });
        
        minMonthTF.setOnAction(event -> {
        //page for results
          try {
            //get inputs from the text field
            String[] inputResults = minMonthTF.getText().split(",");
            //clear the screen and print the results
            resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
            resultsVBox.getChildren().add(createLabel("Results for Minimum Weights for " + minMonthTF.getText(), "Chalkduster", FontWeight.BOLD, 20));
            resultsVBox.getChildren().add(createLabel("January Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("February Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("March Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("April Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("May Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("June Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("July Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("August Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("September Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("October Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("November Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            resultsVBox.getChildren().add(createLabel("December Max: " + dataManager.getMonthlyMin(), "Times New Roman", FontWeight.BOLD, 15));
            panelResults.setCenter(resultsVBox);
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          }catch(ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"farmID,year\"");
            alert.showAndWait();
          }
          });
        
        minAllFarmsTF.setOnAction(event -> {
        //page for results
          try {
            //get inputs from the text field
            String[] inputResults = minAllFarmsTF.getText().split(",");
            //clear the screen and print the results
            resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
            resultsVBox.getChildren().add(createLabel("Results for Minimum Weights for " + maxAllFarmsTF.getText(), "Chalkduster", FontWeight.BOLD, 20));
           
            //get all of the farms and print out their maximums for the month
            ArrayList<Farm> farms = cheeseFactory.getFarmList();
            for(int i = 0; i < farms.size(); ++i)
              resultsVBox.getChildren().add(createLabel("Farm: " + farms.get(i).getFarmID() + ": "+ dataManager.getMonthlyMaxForFarm(), "Times New Roman", FontWeight.BOLD, 15));
            
            //change the scene to the results scene
            panelResults.setCenter(resultsVBox);
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          }catch(ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"farmID,year\"");
            alert.showAndWait();
          }
          });
        
        avgMonthTF.setOnAction(event -> {
          //page for results
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          });
        
        avgAllFarmsTF.setOnAction(event -> {
          //page for results
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          });
        
        pctgMonth.setOnAction(event -> {
          //page for results
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          });
        
        pctgYear.setOnAction(event -> {
          //page for results
            primaryStage.setScene(resultsScene);
            primaryStage.show();
          });
		
		
		
		
		
		
		
		
		
		
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
