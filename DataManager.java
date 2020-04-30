package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DataManager {

	public CheeseFactory factory;
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

	public DataManager() {
		factory = new CheeseFactory();
		fmng = new FileManager();
	}

	public boolean readFile(String inputFile) throws IOException {
		return fmng.readFile(inputFile, factory);
	}

	/**
	 * This returns a vector of length 12 representing the average weight of milk
	 * produced by all farms in each month of the year
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
	 * This returns a vector of length 12 representing the weight of milk produced
	 * by all farms in each month of the year
	 * 
	 * @param year
	 * @return
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

	private double[] intToDouble(int[] array) {
		int size = array.length;
		double[] doubleArr = new double[size];
		for (int i = 0; i < size; i++) {
			doubleArr[i] = array[i];
		}
		return doubleArr;
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
	
	public void writeFarmReport(String farmID, int year, String path) {
	  
	  try {
      FileWriter writer1 = new FileWriter(path);
      PrintWriter writer = new PrintWriter(writer1);
      for(int i = 0; i < farmMonthlyReport(year).length; i++) {
        
        if(farmMonthlyReport(year)[i].farmID.equals(farmID)) {
          //System.out.println(farmMonthlyReport(year)[i].monthlyReport[2]);
          for(int j = 0; j < farmMonthlyReport(year)[i].monthlyReport.length; j++) {
            writer.println("Month " + (j + 1) + " Weight: " + farmMonthlyReport(year)[i].monthlyReport[j]);
            
            
          }
        }
        
      }      
      writer.close();
    } catch (IOException e) {
      
      e.printStackTrace();
    }
	  
	  
	  
	}

	public double getMonthlyMin(int year) {
		double[] monthlyReport = getMonthlySumVec(year);
		return getArrayMinValue(monthlyReport);
	}

	public double getMonthlyMax(int year) {
		double[] monthlyReport = getMonthlySumVec(year);
		return getArrayMaxValue(monthlyReport);
	}

	private double getArrayMinValue(double[] array) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < 12; i++)
			if (array[i] < min)
				min = array[i];
		return min;
	}

	private double getArrayMaxValue(double[] array) {
		double max = -1;
		for (int i = 0; i < 12; i++)
			if (array[i] > max)
				max = array[i];
		return max;
	}

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

	public int getMonthlyMinForFarm(String farmID, int year) {
		double[] monthlySumForFarm = intToDouble(getMonthlySumForFarm(farmID, year));
		return (int) getArrayMinValue(monthlySumForFarm);

	}

	public int getMonthlyMaxForFarm(String farmID, int year) {
		double[] monthlySumForFarm = intToDouble(getMonthlySumForFarm(farmID, year));
		return (int) getArrayMaxValue(monthlySumForFarm);
	}

		/**
	 * Return the percentage of milk that a farm contributed(with respect to all
	 * farms) on monthly basis.
	 * 
	 * @param year
	 * @param month
	 * @return a vector of length 12 representing the percentage in each month
	 */
	public double[] percentageVectorMonth(String farmID, int year) {
		double[] allFarmsVec = getMonthlySumVec(year);
		int[] farmVec = getMonthlySumForFarm(farmID, year);
		return vecDivision(intToDouble(farmVec), allFarmsVec);
	}

	private double[] vecDivision(double[] numerator, double[] denominator) {
		if (numerator.length != denominator.length)
			return null;
		int vecLength = numerator.length;
		double[] ratio = new double[vecLength];
		for (int i = 0; i < vecLength; i++) {
			ratio[i] = numerator[i] / denominator[i];
		}
		return ratio;
	}

	/**
	 * Return the percentage of milk that a farm contributed(with respect to all
	 * farms) in the year specified.
	 * 
	 * @param year
	 * @return
	 */
	public double percentageVectorYear(String farmID, int year) {
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
	public double percentageVectorAllTime(String farmID) {
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

	public int getSingleData(String farmID, int year, int month, int day) {
		return factory.getSingleData(farmID, year, month, day);
	}

	public void writeFarmReport(String farmID, int year) {
		for (int i = 0; i < farmMonthlyReport(year).length; i++) {
			if (farmMonthlyReport(year)[i].farmID.equals(farmID)) {
				System.out.println(farmMonthlyReport(year)[i]);
			}
		}
	}

	/**
	 * Gets the factory
	 * 
	 * @return the cheese factory
	 */
	public CheeseFactory getCheeseFactory() {
		return factory;
	}

}
