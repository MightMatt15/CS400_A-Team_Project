package application;

import java.io.IOException;
import java.util.ArrayList;

public class DataManager {

	private CheeseFactory factory;
	private FileManager fmng;

	/**
	 * This is a class to store the monthly sum of weight of a farm in a year.
	 */
	private class farmMonthlyReport {
		String farmID;
		int[] monthlyReport;

		private farmMonthlyReport(String farmID, int[] monthlyReport) {
			this.farmID = farmID;
			this.monthlyReport = monthlyReport;
		}
	}

	public DataManager(String[] inputFiles) throws IOException {
		factory = new CheeseFactory();
		fmng = new FileManager();
		// initialize cheese factory using data in the files
		for (String inputFile : inputFiles)
			fmng.readFile(inputFile, factory);
	}

	/**
	 * This returns a vector of length 12 representing the average weight of milk
	 * produced by all farms (in the year specified)
	 * 
	 * @param year
	 * @return
	 */
	public double[] getMonthlyAvgVec(int year) {
		int numberOfFarms = factory.getFactorySize();
		ArrayList<Farm> farmList = factory.getFarmList();
		double[] monthlyAvgVec = new double[12];
		for (int i = 0; i < numberOfFarms; i++) {
			vecAddition(monthlyAvgVec, farmList.get(i).monthlyAvg(year));
		}
		return monthlyAvgVec;
	}

	/**
	 * Vector addition.
	 * 
	 * @param vecS1
	 * @param vecS2
	 * @return vecS1 = vecS1 + vecS2
	 */
	private double[] vecAddition(double[] vecS1, double[] vecS2) {
		if (vecS1.length != vecS2.length)
			return null;
		for (int i = 0; i < vecS1.length; i++) {
			vecS1[i] += vecS2[i];
		}
		return vecS1;
	}

	/**
	 * Return the monthly report of the year.
	 * 
	 * @param year
	 * @return monthly report of the year
	 */
	private farmMonthlyReport[] farmMonthlyReport(int year) {
		int numberOfFarms = factory.getFactorySize();
		farmMonthlyReport[] farmReportByMonth = new farmMonthlyReport[numberOfFarms];
		ArrayList<Farm> farmList = factory.getFarmList();
		for (int i = 0; i < numberOfFarms; i++) {
			Farm farm = farmList.get(i);
			farmReportByMonth[i] = new farmMonthlyReport(farm.getFarmID(),
					farm.monthlySum(year));
		}
		// what's inside farmMonthlyReport[]:
		// {String: farm1, int[12]: [0: Jan.data, 1: Feb.data, ..., 11: Dec.data]}
		// {String: farm2, int[12]: [0: Jan.data, 1: Feb.data, ..., 11: Dec.data]}
		// {String: farmN, int[12]: [0: Jan.data, 1: Feb.data, ..., 11: Dec.data]}
		return farmReportByMonth;
	}

	public int getMonthlyMin(int year) {

		return 0;
	}

	public int getMonthlyMax(int year) {

		return 0;
	}

	public int getMonthlyAverageForFarm() {

		return 0;
	}

	public int getMonthlyMinForFarm() {

		return 0;
	}

	public int getMonthlyMaxForFarm() {

		return 0;
	}

	public int getDataSortedByField() {

		return 0;
	}

	public int getAverageInDateRange() {

		return 0;
	}

	public int getMinInDateRange() {

		return 0;
	}

	public int getMaxInDateRange() {

		return 0;
	}
}
