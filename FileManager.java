package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

  public String inputFile;
  public String outputFile;
  public CheeseFactory myFactory;
  
  public FileManager(CheeseFactory factory) {
    myFactory = factory;
  }
  
  

  private static String getMonth(int monthInt) {
    String month = null;
    if (monthInt == 1) {
      month = "January";
    } else if (monthInt == 2) {
      month = "February";
    } else if (monthInt == 3) {
      month = "March";
    } else if (monthInt == 4) {
      month = "April";
    } else if (monthInt == 5) {
      month = "May";
    } else if (monthInt == 6) {
      month = "June";
    } else if (monthInt == 7) {
      month = "July";
    } else if (monthInt == 8) {
      month = "August";
    } else if (monthInt == 9) {
      month = "September";
    } else if (monthInt == 10) {
      month = "October";
    } else if (monthInt == 11) {
      month = "November";
    } else if (monthInt == 12) {
      month = "December";
    }

    return month;

  }


  public boolean readFile(String inputFile) throws IOException {
    File file = null;
    // this.inputFile = inputFile;
    file = new File(inputFile);
    Scanner realFile = new Scanner(file);

    try {

      while (realFile.hasNextLine()) {


        String currentLine = null;
        currentLine = realFile.nextLine().toString();
        if (currentLine.contains("date")) {

        } else {
          String farmNumber = null;
          int month = 0;
          int day = 0;
          int year = 0;
          int weight = 0;
          StringBuilder myBuilder = new StringBuilder();
          myBuilder.append(currentLine);
          try {
            year = Integer.parseInt(myBuilder.substring(0, 4));
          } catch (Exception e) {
            year = 0;
          }
          myBuilder.delete(0, 5);
          if (myBuilder.substring(0, 2).contains("-")) {
            try {
              // month = getMonth(Integer.parseInt(myBuilder.substring(0,1)));
              month = Integer.parseInt(myBuilder.substring(0, 1));
              myBuilder.delete(0, 2);
            } catch (Exception e) {
              month = 0;
            }
          } else {
            try {
              month = Integer.parseInt(myBuilder.substring(0, 2));
              myBuilder.delete(0, 3);
            } catch (Exception e) {
              month = 0;
            }

          }
          if (myBuilder.substring(0, 2).contains(",")) {
            try {
              day = Integer.parseInt(myBuilder.substring(0, 1));
              myBuilder.delete(0, 2);
            } catch (Exception e) {
              day = 0;
            }
          } else {
            try {
              day = Integer.parseInt(myBuilder.substring(0, 2));
              myBuilder.delete(0, 3);
            } catch (Exception e) {
              day = 0;
            }
          }

          farmNumber = myBuilder.substring(0, myBuilder.indexOf(","));
          myBuilder.delete(0, myBuilder.indexOf(",") + 1);
          try {
            weight = Integer.parseInt(myBuilder.toString());
          } catch (Exception e) {
            weight = 0;
          }
          myFactory.insertSingleData(farmNumber, year, month, day, weight);
          System.out.println("Year: " + year + " Month: " + month +  " Day: " + day + " Farm Number: "
              + farmNumber + " Weight: " + weight);
        }


      }
      return true;
    } catch (Exception e) {
      return false;
    } finally {


      if (realFile != null) {
        realFile.close();
      }
    }


  }

  public boolean writeToFile() {

    return false;
  }

  public String getFileContents() {

    return null;
  }
}
