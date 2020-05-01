///////////////////////////////////////////////////////////////////////////////
// Title: FileManager.java
// Files: CheeseFactory.java, DataManager.java, Farm.java, GUI.java
//        
// Course: CS 400, SP2020
//
// Authors: Adam Pryor        Matt McNaught		  Zhiyuan Lei
// Email:   adpryor@wisc.edu  mmcnaught@wisc.edu  zlei23@wisc.edu
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

public class FileManager {

	public String inputFile;
	public String outputFile;


	public boolean readFile(String inputFile, CheeseFactory myFactory) throws IOException {
		File file = null;
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
							// month =
							// getMonth(Integer.parseInt(myBuilder.substring(0,1)));
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
					System.out.println("Year: " + year + " Month: " + month + " Day: "
							+ day + " Farm Number: " + farmNumber + " Weight: " + weight);
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

	public boolean writeToFile(String farmID, int year, CheeseFactory factory) {

	  
		return false;
	}

	public String getFileContents() {

		return null;
	}
}
