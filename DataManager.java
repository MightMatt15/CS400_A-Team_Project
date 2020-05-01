package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DataManager {

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

	// private fields //
	public CheeseFactory factory;
	private FileManager fmng;

	/**
	 * Default constructor of Data Manager
	 */
	public DataManager() {
		factory = new CheeseFactory();
		fmng = new FileManager();
	}

	/**
	 * Gets the factory.
	 * 
	 * @return the cheese factory
	 */
	public CheeseFactory getCheeseFactory() {
		return factory;
	}

	/**
	 * Get a single data of a farm value on the date.
	 * 
	 * @param farmID
	 * @param year
	 * @param month
	 * @param day
	 * @return the amount of milk produced by the farm on that date
	 */
	public int getSingleData(String farmID, int year, int month, int day) {
		return factory.getSingleData(farmID, year, month, day);
	}

////////////////////////////////////////////////////////////////////////////////
///////////////////////////////File I-O//////////////////////////////////////////
	/**
	 * Initialize cheese factory using the input file.
	 * 
	 * @param inputFile data file name
	 * @return true if file was read successfully; false otherwise.
	 * @throws IOException if reading file caused IO exception
	 */
	public boolean readFile(String inputFile) throws IOException {
		return fmng.readFile(inputFile, factory);
	}

	/**
	 * Write farm report to an output file.
	 * 
	 * @param farmID farm identifier
	 * @param year   year
	 * @param path   output file path
	 */
	public void writeFarmReport(String farmID, int year, String path) {
		try {
			FileWriter writer1 = new FileWriter(path);
			PrintWriter writer = new PrintWriter(writer1);
			for (int i = 0; i < farmMonthlyReport(year).length; i++) {

				if (farmMonthlyReport(year)[i].farmID.equals(farmID)) {
					for (int j = 0; j < farmMonthlyReport(
							year)[i].monthlyReport.length; j++) {
						writer.println("Month " + (j + 1) + " Weight: "
								+ farmMonthlyReport(year)[i].monthlyReport[j]
								+ " Percent of total milk for month: "
								+ percentageVectorMonthForFarm(farmID, year)[j] * 100
								+ "%");
					}
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
///////////////////////////////File I-O//////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
//////////////////////Monthly Report Across All Farms///////////////////////////
	/**
	 * This returns a vector of length 12 representing the average weight of milk
	 * produced by ALL FARMS in each month of the year
	 * 
	 * @param year
	 * @return a vector of length 12, each representing a month of data
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
	 * This returns a vector of length 12 representing the weight of milk produced
	 * by ALL FARMS in each month of the year
	 * 
	 * @param year
	 * @return a vector of length 12, each representing a month of data
	 */
	public double[] getMonthlySumVec(int year) {
		int numberOfFarms = factory.getFactorySize();
		ArrayList<Farm> farmList = factory.getFarmList();
		double[] monthlySumVec = new double[12];
		for (int i = 0; i < numberOfFarms; i++) {
			double[] monthVec = intToDouble(farmList.get(i).monthlySum(year));
			vecAddition(monthlySumVec, monthVec);
		}
		return monthlySumVec;
	}

	/**
	 * Return the monthly report of the year. Helper method.
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

	/**
	 * Return the minimum monthly total weight(i.e across ALL FARMS) in the year.
	 * 
	 * @param year year
	 * @return the minimum monthly total weight in the year
	 */
	public double getMonthlyMin(int year) {
		double[] monthlyReport = getMonthlySumVec(year);
		return getArrayMinValue(monthlyReport);
	}

	/**
	 * Return the maximum monthly total weight(i.e across ALL FARMS) in the year.
	 * 
	 * @param year year
	 * @return the maximum monthly total weight in the year
	 */
	public double getMonthlyMax(int year) {
		double[] monthlyReport = getMonthlySumVec(year);
		return getArrayMaxValue(monthlyReport);
	}
//////////////////////Monthly Report Across All Farms///////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
/////////////////////////Monthly Report For A Farm//////////////////////////////
	/**
	 * Return the monthly report(monthly average) of a farm in the give year.
	 * 
	 * @param farmID farm identifier
	 * @param year
	 * @return a vector of length 12 representing the average amount of milk
	 *         produced by this farm in every month of the year
	 */
	public double[] getMonthlyAverageForFarm(String farmID, int year) {
		ArrayList<Farm> listOfFactories = factory.getFarmList();
		for (Farm f : listOfFactories) {
			if (f.getFarmID().equals(farmID))
				return f.monthlyAvg(year);
		}
		return null; // the farm with the specified identifier was not found
	}

	/**
	 * Return the maximum daily production of each month for the farm and year
	 * specified.
	 * 
	 * @param farmID farm identifier
	 * @param year   year
	 * @return a vector of length 12 representing maximum daily production of each
	 *         month
	 */
	public int[] getMaxDaysForFarm(String farmID, int year) {
		Farm farm = factory.getFarm(farmID);
		int[] maxDays = farm.getMaxDays(year);
		for (int i = 0; i < 12; i++)
			if (maxDays[i] == -1)
				maxDays[i] = 0;
		return maxDays;
	}

	/**
	 * Return the minimum daily production of each month for the farm and year
	 * specified.
	 * 
	 * @param farmID farm identifier
	 * @param year   year
	 * @return a vector of length 12 representing minimum daily production of each
	 *         month
	 */
	public int[] getMinDaysForFarm(String farmID, int year) {
		Farm farm = factory.getFarm(farmID);
		int[] minDays = farm.getMinDays(year);
		for (int i = 0; i < 12; i++)
			if (minDays[i] == Integer.MAX_VALUE)
				minDays[i] = 0;
		return minDays;
	}

	/**
	 * Return the monthly report(monthly sum) of a farm in the give year.
	 * 
	 * @param farmID farm identifier
	 * @param year
	 * @return a vector of length 12 representing the total amount of milk produced
	 *         by this farm every month of the year
	 */
	public int[] getMonthlySumForFarm(String farmID, int year) {
		ArrayList<Farm> listOfFactories = factory.getFarmList();
		for (Farm f : listOfFactories) {
			if (f.getFarmID().equals(farmID))
				return f.monthlySum(year);
		}
		return null; // the farm with the specified identifier was not found
	}

	/**
	 * Return the minimum monthly amount produced by the farm during the year.
	 * 
	 * @param farmID
	 * @param year
	 * @return minimum monthly amount produced by the farm during the year
	 */
	public int getMonthlyMinForFarm(String farmID, int year) {
		double[] monthlySumForFarm = intToDouble(getMonthlySumForFarm(farmID, year));
		return (int) getArrayMinValue(monthlySumForFarm);
	}

	/**
	 * Return the maximum monthly amount produced by the farm during the year.
	 * 
	 * @param farmID
	 * @param year
	 * @return maximum monthly amount produced by the farm during the year
	 */
	public int getMonthlyMaxForFarm(String farmID, int year) {
		double[] monthlySumForFarm = intToDouble(getMonthlySumForFarm(farmID, year));
		return (int) getArrayMaxValue(monthlySumForFarm);
	}
/////////////////////////Monthly Report For A Farm//////////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
/////////////////////////Percentage Report For A Farm///////////////////////////
	/**
	 * Return the percentage of milk that a farm contributed(with respect to all
	 * farms) on monthly basis.
	 * 
	 * @param year
	 * @param month
	 * @return a vector of length 12 representing the percentage in each month
	 */
	public double[] percentageVectorMonthForFarm(String farmID, int year) {
		double[] allFarmsVec = getMonthlySumVec(year);
		int[] farmVec = getMonthlySumForFarm(farmID, year);
		return vecDivision(intToDouble(farmVec), allFarmsVec);
	}

	/**
	 * Return the percentage of milk that a farm contributed(with respect to all
	 * farms) in the year specified.
	 * 
	 * @param year
	 * @return
	 */
	public double percentageYearForFarm(String farmID, int year) {
		int annualSum_All = factory.annualSumAllFarms(year);
		Farm farm = factory.getFarm(farmID);
		double annualSum_farm = farm.annualSum(year);
		return annualSum_farm / annualSum_All;
	}

	/**
	 * Return the percentage of milk that a farm contributed(with respect to all
	 * farms) throughout the time.
	 * 
	 * @return
	 */
	public double percentageAllTimeForFarm(String farmID) {
		int sumAll = 0;
		int sumFarm = 0;
		ArrayList<Integer> yearList = factory.getYearList();
		Farm farm = factory.getFarm(farmID);
		for (Integer year : yearList) {
			sumAll += factory.annualSumAllFarms(year);
			sumFarm += farm.annualSum(year);
		}
		return (double) sumFarm / sumAll * 100;
	}

/////////////////////////Percentage Report For A Farm///////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
/////////////////////////Random Time Span Report////////////////////////////////
	/**
	 * Calculate the average amount of milk (i.e per day) produced by the farm
	 * during a range of time.
	 * 
	 * @param farmID farm identifier
	 * @param yStart year of starting date
	 * @param mStart month of starting date
	 * @param dStart day of starting date
	 * @param yEnd   year of ending date
	 * @param mEnd   month of ending date
	 * @param dEnd   day of ending date
	 * @return the average amount of milk (i.e per day) produced by the farm during
	 *         a range of time
	 */
	public double getAvgInRangeForFarm(String farmID, int yStart, int mStart, int dStart,
			int yEnd, int mEnd, int dEnd) {
		ArrayList<Farm> listOfFactories = factory.getFarmList();
		for (Farm f : listOfFactories) {
			if (f.getFarmID().equals(farmID))
				return f.avgWithinRange(yStart, mStart, dStart, yEnd, mEnd, dEnd);
		}
		return 0;
	}

	/**
	 * Sum up the amount of milk produced by all farms during the time span.
	 * 
	 * @param yStart year of starting date
	 * @param mStart month of starting date
	 * @param dStart day of starting date
	 * @param yEnd   year of ending date
	 * @param mEnd   month of ending date
	 * @param dEnd   day of ending date
	 * @return the total amount of milk produced by all farms during the time span
	 */
	public int getSumInRangeAllFarms(int yStart, int mStart, int dStart, int yEnd,
			int mEnd, int dEnd) {
		int sum = 0;
		ArrayList<Farm> listOfFactories = factory.getFarmList();
		for (Farm f : listOfFactories) {
			sum += f.sumWithinRange(yStart, mStart, dStart, yEnd, mEnd, dEnd)[0];
		}
		return sum;
	}

	/**
	 * Sum up the amount of milk produced by each farms during the time span.
	 * 
	 * @param yStart year of starting date
	 * @param mStart month of starting date
	 * @param dStart day of starting date
	 * @param yEnd   year of ending date
	 * @param mEnd   month of ending date
	 * @param dEnd   day of ending date
	 * @return total amount of milk produced by each farm during the time span
	 */
	public double[] getSumInRangeEachFarm(int yStart, int mStart, int dStart, int yEnd,
			int mEnd, int dEnd) {
		int factorySize = factory.getFactorySize();
		double[] sumInRangeEachFarm = new double[factorySize];
		ArrayList<Farm> listOfFactories = factory.getFarmList();
		int i = 0;
		for (Farm f : listOfFactories) {
			sumInRangeEachFarm[i++] = (double) f.sumWithinRange(yStart, mStart, dStart,
					yEnd, mEnd, dEnd)[0];
		}
		return sumInRangeEachFarm;
	}

	/**
	 * Calculate the minimal contribution(among all farms) during the time span.
	 * 
	 * @param yStart year of starting date
	 * @param mStart month of starting date
	 * @param dStart day of starting date
	 * @param yEnd   year of ending date
	 * @param mEnd   month of ending date
	 * @param dEnd   day of ending date
	 * @return the minimal contribution during the time span
	 */
	public double getMinInRange(int yStart, int mStart, int dStart, int yEnd, int mEnd,
			int dEnd) {
		double[] sumInRangeEachFarm = getSumInRangeEachFarm(yStart, mStart, dStart, yEnd,
				mEnd, dEnd);
		return getArrayMinValue(sumInRangeEachFarm);
	}

	/**
	 * Calculate the maximum contribution(among all farms) during the time span.
	 * 
	 * @param yStart year of starting date
	 * @param mStart month of starting date
	 * @param dStart day of starting date
	 * @param yEnd   year of ending date
	 * @param mEnd   month of ending date
	 * @param dEnd   day of ending date
	 * @return the maximum contribution during the time span
	 */
	public double getMaxInRange(int yStart, int mStart, int dStart, int yEnd, int mEnd,
			int dEnd) {
		double[] sumInRangeEachFarm = getSumInRangeEachFarm(yStart, mStart, dStart, yEnd,
				mEnd, dEnd);
		return getArrayMaxValue(sumInRangeEachFarm);
	}

	/**
	 * Calculate the percentage contribution of every farm during the time span.
	 * 
	 * @param yStart year of starting date
	 * @param mStart month of starting date
	 * @param dStart day of starting date
	 * @param yEnd   year of ending date
	 * @param mEnd   month of ending date
	 * @param dEnd   day of ending date
	 * @return the percentage contribution of every farm during the time span
	 */
	public double[] getPercentageInRangeAllFarms(int yStart, int mStart, int dStart,
			int yEnd, int mEnd, int dEnd) {
		int sumInRangeAllFarms = getSumInRangeAllFarms(yStart, mStart, dStart, yEnd, mEnd,
				dEnd);
		double[] sumInRangeEachFarm = getSumInRangeEachFarm(yStart, mStart, dStart, yEnd,
				mEnd, dEnd);
		int factorySize = sumInRangeEachFarm.length;
		double[] percentgeInRangeEachFarm = new double[factorySize];
		for (int i = 0; i < factorySize; i++)
			percentgeInRangeEachFarm[i] = sumInRangeEachFarm[i] / sumInRangeAllFarms
					* 100;
		return percentgeInRangeEachFarm;
	}
/////////////////////////Random Time Span Report////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
///////////////////////////////Helper Functions/////////////////////////////////
	/**
	 * Return the minimum value in the array.
	 * 
	 * @param array
	 * @return the minimum value in the array; -1 if array is null
	 */
	private double getArrayMinValue(double[] array) {
		if (array == null)
			return -1;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < 12; i++)
			if (array[i] < min)
				min = array[i];
		return min;
	}

	/**
	 * Return the maximum value in the array.
	 * 
	 * @param array
	 * @return the maximum value in the array; -1 if array is null
	 */
	private double getArrayMaxValue(double[] array) {
		if (array == null)
			return -1;
		double max = -1;
		for (int i = 0; i < 12; i++)
			if (array[i] > max)
				max = array[i];
		return max;
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
	 * Vector division.
	 * 
	 * @param numerator
	 * @param denominator
	 * @return numerator / denominator
	 */
	private double[] vecDivision(double[] numerator, double[] denominator) {
		if (numerator.length != denominator.length)
			return null;
		int vecLength = numerator.length;
		double[] ratio = new double[vecLength];
		for (int i = 0; i < vecLength; i++) {
			ratio[i] = numerator[i] / denominator[i];
			if (Double.isNaN(ratio[i])) {
				ratio[i] = 0;
			}
		}
		return ratio;
	}

	/**
	 * Convert integer type array to double type array.
	 * 
	 * @param array array to be converted
	 * @return a double array; null if argument is null
	 */
	private double[] intToDouble(int[] array) {
		if (array == null)
			return null;
		int size = array.length;
		double[] doubleArr = new double[size];
		for (int i = 0; i < size; i++) {
			doubleArr[i] = array[i];
		}
		return doubleArr;
	}
///////////////////////////////Helper Functions/////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
}
