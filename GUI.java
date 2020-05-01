///////////////////////////////////////////////////////////////////////////////
// Title: GUI.java
// Files: CheeseFactory.java, DataManager.java, farm.java, FileManager.java
//        
// Course: CS 400, SP2020
//
// Authors: Adam Pryor        Matt McNaught		    Zhiyuan Lei
// Email:   adpryor@wisc.edu  mmcnaught@wisc.edu  zlei23@wisc.edu
// Lecturer's Name: Debra Deppeler
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
// None
////////////////////////////////////////////////////////////////////////////////

package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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

/*
 * This class runs and manages a GUI for a program that reads Milk Weight data, 
 * manages it, and displays
 */
public class GUI extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  private static final int WINDOW_WIDTH = 700;
  private static final int WINDOW_HEIGHT = 500;
  // manage data provided in files for visualization and manipulation
  DataManager dataManager = new DataManager();
  // format for all doubles printed
  DecimalFormat df = new DecimalFormat("#.00");

  @Override
  public void start(Stage primaryStage) throws Exception {
    // save args example
    // args = this.getParameters().getRaw();

    // dashboard
    BorderPane panel = new BorderPane();

    // change the background color
    Background background =
        new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));
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
    Label searchedData = createLabel("", "Times New Roman", FontWeight.BOLD, 15);

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
    selectionPanel.getChildren().add(searchedData);
    panel.setLeft(selectionPanel);

    search.setOnAction(event -> {// show weight for that day?
      try {
        int weight =
            dataManager.getSingleData(farmInput.getText(), Integer.parseInt(yearInput.getText()),
                Integer.parseInt(monthInput.getText()), Integer.parseInt(dayInput.getText()));
        Alert alert;
        if (weight == -2) {
          alert = new Alert(AlertType.ERROR, "The farmID entered does not exist in the data");
          alert.showAndWait();
        } else if (weight == -1) {
          alert = new Alert(AlertType.ERROR, "The year entered does not exist in the data");
          alert.showAndWait();
        } else {
          // show the data searched for
          searchedData.setText("" + farmInput.getText() + "'s data for that day: " + weight);
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        Alert alert =
            new Alert(AlertType.ERROR, "The day or month entered does not exist in the data");
        alert.showAndWait();
      } catch (NumberFormatException e) {
        Alert alert =
            new Alert(AlertType.ERROR, "Please enter a number for the day, month, and year");
        alert.showAndWait();
      }
    });

    // add a section for showing total, max, min, avg, and percentages
    VBox resultsPanel = new VBox();
    GridPane results = new GridPane();
    results.add(createLabel("Maximum By Month:  ", "Times New Roman", FontWeight.BOLD, 15), 0, 0);
    results.add(createLabel("Maximum All Farms: ", "Times New Roman", FontWeight.BOLD, 15), 0, 1);
    results.add(createLabel("Minimum By Month:  ", "Times New Roman", FontWeight.BOLD, 15), 0, 2);
    results.add(createLabel("Minimum All Farms:    ", "Times New Roman", FontWeight.BOLD, 15), 0,
        3);
    results.add(createLabel("Average By Month:  ", "Times New Roman", FontWeight.BOLD, 15), 0, 4);
    results.add(createLabel("Average All Farms: ", "Times New Roman", FontWeight.BOLD, 15), 0, 5);
    results.add(createLabel("Percentage For Month", "Times New Roman", FontWeight.BOLD, 15), 0, 6);
    results.add(createLabel("Percentage For Year:", "Times New Roman", FontWeight.BOLD, 15), 0, 7);
    // results.add(createLabel("Percentage For Year:", "Times New Roman",
    // FontWeight.BOLD, 15), 0, 8);

    TextField maxMonthTF = new TextField("Enter: FarmID,Year");
    TextField maxAllFarmsTF = new TextField("Enter: Month,Year");
    TextField minMonthTF = new TextField("Enter: FarmID,Year");
    TextField minAllFarmsTF = new TextField("Enter: Month,Year");
    TextField avgMonthTF = new TextField("Enter: FarmID,Year");
    TextField avgAllFarmsTF = new TextField("Enter: Month,Year");
    TextField pctgMonth = new TextField("Enter: Month,Year");
    TextField pctgYear = new TextField("Enter: Year");
    // Button pctgTotal = new Button("Farms' Percentage of Total");

    results.add(maxMonthTF, 1, 0);
    results.add(maxAllFarmsTF, 1, 1);
    results.add(minMonthTF, 1, 2);
    results.add(minAllFarmsTF, 1, 3);
    results.add(avgMonthTF, 1, 4);
    results.add(avgAllFarmsTF, 1, 5);
    results.add(pctgMonth, 1, 6);
    results.add(pctgYear, 1, 7);
    // results.add(pctgTotal, 1, 8);
    // add to center of screen
    resultsPanel.getChildren().add(createLabel("Results", "Chalkduster", FontWeight.BOLD, 20));
    resultsPanel.getChildren().add(results);
    results.setAlignment(Pos.TOP_LEFT);
    panel.setCenter(resultsPanel);

    // results screen
    BorderPane panelResults = new BorderPane();
    // change the background color
    panelResults.setBackground(background);
    Scene resultsScene = new Scene(panelResults, WINDOW_WIDTH, WINDOW_HEIGHT);

    // create the boxes to hold the buttons in each corner on the bottom of the dashboard
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
    Background background3 =
        new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));
    panel3.setBackground(background3);
    Label title3 = new Label("Milk Production Input\n  -- Chalet Cheese Factory");
    title3.setFont(Font.font("Marker Felt", FontWeight.EXTRA_BOLD, 25));
    panel3.setTop(title3);
    BorderPane.setAlignment(title3, Pos.CENTER);

    // change the background color
    Background background2 =
        new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));
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

    // button to go back to dashboard from the results scene
    Button dashboard2 = new Button("Go to Dashboard");
    BorderPane.setAlignment(dashboard2, Pos.BOTTOM_RIGHT);


    // input page
    VBox input = new VBox();
    input.getChildren()
        .add(createLabel("Please input file location Below", "Chalkduster", FontWeight.BOLD, 20));
    TextField inputTextField = new TextField("Ex C:/users/myUser/file.txt");
    input.getChildren().add(inputTextField);
    panel3.setCenter(input);
    BorderPane.setAlignment(panel3, Pos.CENTER);

    // create input fields to output files
    VBox outputTypes = new VBox();
    outputTypes.getChildren().add(createLabel("Output Types", "Chalkduster", FontWeight.BOLD, 20));
    outputTypes.getChildren()
        .add(createLabel("Farm Report", "Times New Roman", FontWeight.BOLD, 15));
    outputTypes.getChildren()
    .add(createLabel("Enter format: Farm ID,Year,Path", "Times New Roman", FontWeight.BOLD, 10));
    TextField farmReportOutput = new TextField(
        "e.g. Farm 2,2018,C:\\Test.txt:");
    outputTypes.getChildren().add(farmReportOutput);
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
    dashboard2.setOnAction(e -> {
      primaryStage.setScene(mainScene);
      primaryStage.show();
    });

    // read the input file when a user types in a filename
    inputTextField.setOnAction(e -> {
      try {
        if (dataManager.readFile(inputTextField.getText())) {
          Alert alert = new Alert(AlertType.INFORMATION,
              "Input file "
                  + inputTextField.getText().substring(inputTextField.getText().lastIndexOf('\\'))
                  + " loaded");
          alert.showAndWait();
        } else {
          Alert alert = new Alert(AlertType.ERROR, "Input file was unable to be loaded");
          alert.showAndWait();
        }
      } catch (IOException e1) {
        Alert alert = new Alert(AlertType.ERROR, "Input file was unable to be loaded");
        alert.showAndWait();
      }
    });

    //output for Farm Report
    farmReportOutput.setOnAction(e -> {
      try {
        String thisFarmID =
            farmReportOutput.getText().substring(0, farmReportOutput.getText().indexOf(","));
        String newString =
            farmReportOutput.getText().substring(farmReportOutput.getText().indexOf(",") + 1);
        int thisYear = Integer.valueOf(newString.substring(0, newString.indexOf(",")));
        String filePath = newString.substring(newString.indexOf(",") + 1);
        dataManager.writeFarmReport(thisFarmID, thisYear, filePath);
        Alert alert = new Alert(AlertType.INFORMATION, "The file has been loaded to " + filePath.substring(filePath.lastIndexOf('\\')));
        alert.showAndWait();
      }catch(NumberFormatException exception) {
        Alert alert = new Alert(AlertType.ERROR, "Please enter the year as a number.");
        alert.showAndWait();
      }catch(IOException exception) {
        Alert alert = new Alert(AlertType.ERROR, "File path is not a valid path.");
        alert.showAndWait();
      }catch(StringIndexOutOfBoundsException exception) {
        Alert alert = new Alert(AlertType.ERROR, "File path is not a valid path.");
        alert.showAndWait();
      }
    });


    // box to display resutls in
    VBox resultsVBox = new VBox();
    // button to return to dashboard
    panelResults.setRight(dashboard2);
    BorderPane.setAlignment(dashboard2, Pos.BOTTOM_RIGHT);

    // results
    maxMonthTF.setOnAction(event -> {
      // page for max in a month
      try {
        // get inputs from the text field
        String[] inputResults = maxMonthTF.getText().split(",");
        // maximums for all months
        int[] maximums =
            dataManager.getMaxDaysForFarm(inputResults[0], Integer.parseInt(inputResults[1]));
        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel("Results for Maximum Weights for " + maxMonthTF.getText() + ":\n\n",
                "Chalkduster", FontWeight.BOLD, 25));
        resultsVBox.getChildren()
            .add(createLabel("          January Maximum Weight: " + maximums[0], "Times New Roman",
                FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          February Maximum Weight: " + maximums[1], "Times New Roman",
                FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          March Maximum Weight: " + maximums[2],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          April Maximum Weight: " + maximums[3],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          May Maximum Weight: " + maximums[4],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          June Maximum Weight: " + maximums[5],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          July Maximum Weight: " + maximums[6],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          August Maximum Weight: " + maximums[7],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          September Maximum Weight: " + maximums[8],
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          October Maximum Weight: " + maximums[9], "Times New Roman",
                FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          November Maximum Weight: " + maximums[10],
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          December Maximum Weight: " + maximums[11],
                "Times New Roman", FontWeight.BOLD, 20));
        panelResults.setCenter(resultsVBox);
        BorderPane.setAlignment(resultsVBox, Pos.CENTER);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // entered format wrong
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"FarmID,Year\"");
        alert.showAndWait();
      } catch (NullPointerException e) {
        // entered format wrong
        Alert alert = new Alert(AlertType.ERROR, "Farm does not exist in the data");
        alert.showAndWait();
      }
    });

    maxAllFarmsTF.setOnAction(event -> {
      // page for max for all farms in a month
      try {
        // get inputs from the text field
        String[] inputResults = maxAllFarmsTF.getText().split(",");
        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel("Results for Maximum Weights for " + maxAllFarmsTF.getText() + ":",
                "Chalkduster", FontWeight.BOLD, 25));

        // Setup pie chart
        double myTotal = 0;

        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(new ArrayList<PieChart.Data>());

        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          myTotal = myTotal + dataManager.getMaxDaysForFarm(
              dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
              Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1];
        }
        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          pieChartData.add(new PieChart.Data(
              (dataManager.getCheeseFactory().getFarmList().get(i).getFarmID() + "  "
                  + Math.round(dataManager.getMaxDaysForFarm(
                      dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                      Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1] * 100
                      / myTotal)).toString()
                  + "% Max: "
                  + dataManager.getMaxDaysForFarm(
                      dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                      Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1],
              dataManager.getMaxDaysForFarm(
                  dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                  Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1] * 100
                  / myTotal));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Maximum of all farms during month: "
            + (Integer.valueOf(inputResults[0]) - 1) + " in year: " + (inputResults[1]));
        chart.setLegendVisible(false);

        resultsVBox.getChildren().add(chart);
        // change the scene to the results scene
        panelResults.setCenter(resultsVBox);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // wrong format was entered
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"Month Number,Year\"");
        alert.showAndWait();
      }
    });

    minMonthTF.setOnAction(event -> {
      // page for min of a farm each month in a year
      try {
        // get inputs from the text field
        String[] inputResults = minMonthTF.getText().split(",");
        // minimums for all months
        int[] minimums =
            dataManager.getMaxDaysForFarm(inputResults[0], Integer.parseInt(inputResults[1]));
        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel("Results for Minimum Weights for " + minMonthTF.getText() + ":\n\n",
                "Chalkduster", FontWeight.BOLD, 25));
        resultsVBox.getChildren()
            .add(createLabel("          January Minimum Weight: " + minimums[0], "Times New Roman",
                FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          February Minimum Weight: " + minimums[1], "Times New Roman",
                FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          March Minimum Weight: " + minimums[2],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          April Minimum Weight: " + minimums[3],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          May Minimum Weight: " + minimums[4],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          June Minimum Weight: " + minimums[5],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          July Minimum Weight: " + minimums[6],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren().add(createLabel("          August Minimum Weight: " + minimums[7],
            "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          September Minimum Weight: " + minimums[8],
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          October Minimum Weight: " + minimums[9], "Times New Roman",
                FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          November Minimum Weight: " + minimums[10],
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          December Minimum Weight: " + minimums[11],
                "Times New Roman", FontWeight.BOLD, 20));
        panelResults.setCenter(resultsVBox);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // entered format wrong
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"FarmID,Year\"");
        alert.showAndWait();
      } catch (NullPointerException e) {
        // entered format wrong
        Alert alert = new Alert(AlertType.ERROR, "Farm does not exist in the data");
        alert.showAndWait();
      }
    });

    minAllFarmsTF.setOnAction(event -> {
      // page for min for all farms for a month
      try {
        // get inputs from the text field
        String[] inputResults = minAllFarmsTF.getText().split(",");
        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel("Results for Minimum Weights for " + minAllFarmsTF.getText(),
                "Chalkduster", FontWeight.BOLD, 25));

        // Setup pie chart
        double myTotal = 0;

        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(new ArrayList<PieChart.Data>());

        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          myTotal = myTotal + dataManager.getMinDaysForFarm(
              dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
              Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1];
        }
        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          pieChartData.add(new PieChart.Data(
              (dataManager.getCheeseFactory().getFarmList().get(i).getFarmID() + "  "
                  + Math.round(dataManager.getMinDaysForFarm(
                      dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                      Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1] * 100
                      / myTotal)).toString()
                  + "% Min: "
                  + dataManager.getMinDaysForFarm(
                      dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                      Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1],
              dataManager.getMinDaysForFarm(
                  dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                  Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1] * 100
                  / myTotal));
        }


        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Minimum of all farms during month: "
            + (Integer.valueOf(inputResults[0]) - 1) + " in year: " + (inputResults[1]));
        chart.setLegendVisible(false);



        resultsVBox.getChildren().add(chart);

        // change the scene to the results scene
        panelResults.setCenter(resultsVBox);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // wrong format was entered
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"Month Number,Year\"");
        alert.showAndWait();
      }
    });

    avgMonthTF.setOnAction(event -> {
      // page for avgerage for each month for a farm
      try {
        // get inputs from the text field
        String[] inputResults = avgMonthTF.getText().split(",");
        double[] averages = dataManager.getMonthlyAverageForFarm(inputResults[0],
            Integer.parseInt(inputResults[1]));
        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel("Results for Average Weights for " + avgMonthTF.getText() + ":\n\n",
                "Chalkduster", FontWeight.BOLD, 25));
        resultsVBox.getChildren()
            .add(createLabel("          January Avgerage Weight: " + df.format(averages[0]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          February Avgerage Weight: " + df.format(averages[1]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          March Avgerage Weight: " + df.format(averages[2]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          April Avgerage Weight: " + df.format(averages[3]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          May Avgerage Weight: " + df.format(averages[4]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          June Avgerage Weight: " + df.format(averages[5]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          July Avgerage Weight: " + df.format(averages[6]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          August Avgerage Weight: " + df.format(averages[7]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          September Avgerage Weight: " + df.format(averages[8]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          October Avgerage Weight: " + df.format(averages[9]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          November Avgerage Weight: " + df.format(averages[10]),
                "Times New Roman", FontWeight.BOLD, 20));
        resultsVBox.getChildren()
            .add(createLabel("          December Avgerage Weight: " + df.format(averages[11]),
                "Times New Roman", FontWeight.BOLD, 20));
        panelResults.setCenter(resultsVBox);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // entered format wrong
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"FarmID,Year\"");
        alert.showAndWait();
      } catch (NullPointerException e) {
        // entered format wrong
        Alert alert = new Alert(AlertType.ERROR, "Farm does not exist in the data");
        alert.showAndWait();
      }
    });

    avgAllFarmsTF.setOnAction(event -> {
      // page for avg
      try {
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(new ArrayList<PieChart.Data>());

        double myTotal = 0;

        // get inputs from the text field
        String[] inputResults = avgAllFarmsTF.getText().split(",");
        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          myTotal = myTotal + dataManager.getMonthlyAverageForFarm(
              dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
              Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1];
        }
        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          pieChartData.add(new PieChart.Data(
              (dataManager.getCheeseFactory().getFarmList().get(i).getFarmID() + "  "
                  + Math.round(dataManager.getMonthlyAverageForFarm(
                      dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                      Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1] * 100
                      / myTotal)).toString()
                  + "%",
              dataManager.getMonthlyAverageForFarm(
                  dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                  Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1] * 100
                  / myTotal));
        }


        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Average of all farms during month: "
            + (Integer.valueOf(inputResults[0]) - 1) + " in year: " + (inputResults[1]));
        chart.setLegendVisible(false);

        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel("Results for Minimum Weights for " + avgAllFarmsTF.getText(),
                "Chalkduster", FontWeight.BOLD, 25));
        resultsVBox.getChildren().add(chart);
        // get all of the farms and print out their maximums for the month


        // change the scene to the results scene
        panelResults.setCenter(resultsVBox);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // wrong format was entered
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"Month Number,Year\"");
        alert.showAndWait();
      }
    });

    pctgMonth.setOnAction(event -> {
      // page for percentage of total for a month for all farms
      try {
        // get inputs from the text field
        String[] inputResults = pctgMonth.getText().split(",");
        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel(
                "Results for Percentage of Total Weight for Month: \n\n" + pctgMonth.getText(),
                "Chalkduster", FontWeight.BOLD, 25));



        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(new ArrayList<PieChart.Data>());
        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          pieChartData.add(new PieChart.Data(
              dataManager.getCheeseFactory().getFarmList().get(i).getFarmID() + " "
                  + Math.round(dataManager.percentageVectorMonthForFarm(
                      dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                      Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1] * 100)
                  + "%",
              dataManager.percentageVectorMonthForFarm(
                  dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                  Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0]) - 1]));
          // pieChartData.add(new
          // PieChart.Data(dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
          // dataManager.percentageVectorMonthForFarm(dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
          // Integer.valueOf(inputResults[1]))[Integer.valueOf(inputResults[0])]));

        }


        final PieChart chart = new PieChart(pieChartData);
        chart.setLegendVisible(false);

        resultsVBox.getChildren().add(chart);

        // change the scene to the results scene
        panelResults.setCenter(resultsVBox);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
        // wrong format was entered
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"Month Number,Year\"");
        alert.showAndWait();
      }
    });

    pctgYear.setOnAction(event -> {
      // page for percentage of total for a year for all farms
      try {
        // get inputs from the text field
        String inputResults = pctgYear.getText();
        if(inputResults.equals(""))
          throw new NumberFormatException();
        //test if the input is valid
        int test = Integer.parseInt(inputResults);
        // clear the screen and print the results
        resultsVBox.getChildren().removeAll(resultsVBox.getChildren());
        resultsVBox.getChildren()
            .add(createLabel(
                "Results for Percentage of Total Weight for Year: \n\n" + pctgYear.getText(),
                "Chalkduster", FontWeight.BOLD, 25));

        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(new ArrayList<PieChart.Data>());
        for (int i = 0; i < dataManager.getCheeseFactory().getFarmList().size(); i++) {
          pieChartData.add(new PieChart.Data(
              dataManager.getCheeseFactory().getFarmList().get(i).getFarmID() + " "
                  + Math.round(dataManager.percentageYearForFarm(
                      dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                      Integer.valueOf(inputResults)) * 100)
                  + "%",
              dataManager.percentageYearForFarm(
                  dataManager.getCheeseFactory().getFarmList().get(i).getFarmID(),
                  Integer.valueOf(inputResults))));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Average of all farms during in year: " + (inputResults));
        chart.setLegendVisible(false);

        resultsVBox.getChildren().add(chart);

        // change the scene to the results scene
        panelResults.setCenter(resultsVBox);
        primaryStage.setScene(resultsScene);
        primaryStage.show();
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        // wrong format was entered
        Alert alert = new Alert(AlertType.ERROR, "Please Enter as \"Year\"");
        alert.showAndWait();
      }
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
