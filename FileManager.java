
///////////////////////////////////////////////////////////////////////////////
// Title: FileManager.java
// Files: CheeseFactory.java, DataManager.java, Farm.java, GUI.java
//
// Course: CS 400, SP2020
//
// Authors: Adam Pryor Matt McNaught Zhiyuan Lei
// Email: adpryor@wisc.edu mmcnaught@wisc.edu zlei23@wisc.edu
// Lecturer's Name: Debra Deppeler
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
// None
////////////////////////////////////////////////////////////////////////////////


package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/*
 * This class manages the file reading and writing
 */
public class FileManager {

  public String inputFile;
  public String outputFile;


  /*
   * This method reads an input file and parses and stores the data in Farms in the CheeseFactory
   * 
   * @param inputFile the file to be read
   * @param myFactory the cheese factory that stores all of the farms
   * 
   */
  public boolean readFile(String inputFile, CheeseFactory myFactory) throws IOException {
    File file = null;
    file = new File(inputFile);
    Scanner realFile = new Scanner(file);

    try {

      while (realFile.hasNextLine()) { // Read through entire file

        String currentLine = null;
        currentLine = realFile.nextLine().toString(); 
        if (currentLine.contains("date")) { // Skip first line as it's just formatted data

        } else {
          String farmNumber = null;
          int month = 0;
          int day = 0;
          int year = 0;
          int weight = 0;
          StringBuilder myBuilder = new StringBuilder();
          myBuilder.append(currentLine); // Transform first line into stringbuilder for ease of access
          try {
            year = Integer.parseInt(myBuilder.substring(0, 4)); // Get year
          } catch (Exception e) {
            year = 0; // If exception or invalid input set year to 0
          }
          myBuilder.delete(0, 5); // Delete year from stringbuilder
          if (myBuilder.substring(0, 2).contains("-")) { // Find month separator
            try {
              
              month = Integer.parseInt(myBuilder.substring(0, 1)); // Get month and if invalid set to 0
              myBuilder.delete(0, 2);
            } catch (Exception e) {
              month = 0;
            }
          } else {
            try {
              month = Integer.parseInt(myBuilder.substring(0, 2)); // Get month and if invalid set to 0
              myBuilder.delete(0, 3);
            } catch (Exception e) {
              month = 0;
            }

          }
          if (myBuilder.substring(0, 2).contains(",")) { // Find day separator
            try {
              day = Integer.parseInt(myBuilder.substring(0, 1)); // Get Day and if invalid set to 0
              myBuilder.delete(0, 2);
            } catch (Exception e) {
              day = 0;
            }
          } else {
            try {
              day = Integer.parseInt(myBuilder.substring(0, 2));  // Get Day and if invalid set to 0
              myBuilder.delete(0, 3);
            } catch (Exception e) {
              day = 0;
            }
          }

          farmNumber = myBuilder.substring(0, myBuilder.indexOf(",")); // Get weigh separator and set weight
          myBuilder.delete(0, myBuilder.indexOf(",") + 1);
          try {
            weight = Integer.parseInt(myBuilder.toString());
          } catch (Exception e) {
            weight = 0;
          }
          myFactory.insertSingleData(farmNumber, year, month, day, weight);

        }
      }
      return true;
    } catch (Exception e) {
      return false;
    } finally {

      if (realFile != null) {
        realFile.close(); // Close file
      }
    }

  }

}