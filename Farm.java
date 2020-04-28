package application;

import java.util.ArrayList;

/**
 * This class stores the milk weight data of a farm and manages users' requests
 * for the data.
 *
 */
public class Farm {
	private String farmID; // farm ID can be any string
	// private String owner;
	private ArrayList<annualData> yearList;

	/**
	 * Constructor to initialize a new farm
	 * 
	 * @param farmID farm identifier
	 * @param owner  owner of the farm
	 */
	public Farm(String farmID, String owner) {
		this.farmID = farmID;
		// this.owner = owner;
		yearList = new ArrayList<annualData>();
	}

	/**
	 * This class manages all data of this farm object in a specific year.
	 *
	 */
	private class annualData {
		private int year;
		private int annualSum;
		private int[][] annualData; // 2-d array (12, 31) to representing the data of the
									// year
		private int[] monthlySum; // some of weight of each month during the year

		/**
		 * Constructor
		 */
		private annualData(int year) {
			this.year = year;
			annualData = new int[12][31];
			monthlySum = new int[12];
		}

		/**
		 * Add weight to a date in this year. (cumulative)
		 * 
		 * @param month  month of date
		 * @param day    day of date
		 * @param weight amount of milk added to this date
		 */
		private void addData(int month, int day, int weight) {
			annualData[month - 1][day - 1] += weight;
			annualSum += weight;
			monthlySum[month - 1] += weight;
		}

		/**
		 * Edit weight to a date in this year.
		 * 
		 * @param month  month of date
		 * @param day    day of date
		 * @param weight amount of milk produced by this farm on this date
		 */
		private void editData(int month, int day, int weight) {
			int originalWeight = annualData[month - 1][day - 1];
			annualData[month - 1][day - 1] = weight;
			annualSum += (weight - originalWeight);
			monthlySum[month - 1] += (weight - originalWeight);
		}

		private double getAnnualAverage(int year) {
			int numberofDays = (year % 4 == 0) ? 366 : 365;
			return (double) annualSum / numberofDays;
		}

		private double getMontlyAverage(int month) {
			int numberofDays = 0;
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				numberofDays = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				numberofDays = 30;
			case 2:
				numberofDays = (year % 4 == 0) ? 29 : 28;
				break;
			}
			return (double) monthlySum[month - 1] / numberofDays;
		}

		/**
		 * Return the amount of milk produced by this farm on this date
		 * 
		 * @param month month of date
		 * @param day   day of date
		 * @return the amount of milk produced by this farm on this date of the year
		 */
		private int getData(int month, int day) {
			return annualData[month - 1][day - 1];
		}

		/**
		 * Return the total amount of milk produced by this farm throughout this year.
		 * 
		 * @return the total amount of milk produced by this farm throughout this year
		 */
		private int getAnnualSum() {
			return annualSum;
		}

		/**
		 * Return the total amount of milk produced by this farm in this month of the
		 * year.
		 * 
		 * @return the total amount of milk produced by this farm in this month of the
		 *         year
		 */
		private int getMonthlySum(int month) {
			return monthlySum[month - 1];
		}

		/**
		 * Return the total amount of milk produced by this farm in this period of time
		 * in this year.
		 * 
		 * @param mStart month of starting date
		 * @param mEnd   month of ending date
		 * @param dStart day of starting date
		 * @param dEnd   day of ending date
		 * @return
		 */
		private int getIntervalSum(int mStart, int mEnd, int dStart, int dEnd) {
			int sum = 0;
			int m = 0;
			int d = dStart - 1;
			for (m = mStart - 1; m < 12; m++) {
				for (; d < 31; d++) {
					if (m == mEnd && d == dEnd)
						return sum;
					sum += annualData[m][d];
				}
				d = 0;
			}
			return -1; // fail
		}

		/**
		 * Return the year which the data refer to.
		 * 
		 * @return this year
		 */
		private int getYear() {
			return year;
		}

		private void clearAll() {
			annualSum = 0;
			annualData = new int[12][31];
			monthlySum = new int[12];
		}
	}

	/**
	 * Inserts data for milk on a specific date given that all arguments are valid.
	 * 
	 * @param year   year the date is in
	 * @param month  month the date to edit is in
	 * @param day    day to edit
	 * @param weight weight of the milk for the day
	 * @returns true if inserted successfully
	 * @throws ArrayIndexOutOfBoundsException if the day to access is invalid
	 */
	public boolean insertMilkForDate(int year, int month, int day, int weight)
			throws ArrayIndexOutOfBoundsException {
		annualData reportofYear = yearExists(year);
		if (reportofYear == null) {
			annualData mdata = new annualData(year);
			mdata.addData(month, day, weight);
			yearList.add(mdata);
		} else {
			reportofYear.addData(month, day, weight);
		}
		return true;
	}

	/**
	 * Calculate the total amount of milk produced in this farm during a certain
	 * period. Starting date must be earlier than ending date.
	 * 
	 * @param yStart year of starting date
	 * @param mStart month of starting date
	 * @param dStart day of starting date
	 * @param yEnd   year of ending date
	 * @param mEnd   month of ending date
	 * @param dEnd   day of ending date
	 * @return total amount of milk
	 */
	public int sumWithinRange(int yStart, int mStart, int dStart, int yEnd, int mEnd,
			int dEnd) {
		int sum = 0;
		int[] nextDate;
		annualData year = null;

		while (dateToInt(yStart, mStart, dStart) <= dateToInt(yEnd, mEnd, dEnd)) {
			year = yearExists(yStart);
			if (year == null) { // year does not exist; go to the next year
				yStart++;
				mStart = 1;
				dStart = 1;
				continue;
			}
			sum += year.getData(mStart, dStart);
			nextDate = nextDate(yStart, mStart, dStart); // increment the date
			yStart = nextDate[0];
			mStart = nextDate[1];
			dStart = nextDate[2];
		}
		return sum;
	}

	/**
	 * Edit the milk weight data on the date specified.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public void editWeight(int year, int month, int day, int weight) {
		annualData yearReport = yearExists(year);
		if (yearReport == null) { // the date has not been recorded
			yearReport = new annualData(year);
			yearList.add(yearReport);
		}
		yearReport.editData(month, day, weight);
	}

	/**
	 * Zero the milk weight data on the date specified.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public void removeMilkForDate(int year, int month, int day) {
		editWeight(year, month, day, 0);
	}

	/**
	 * Convert date into an integer representing.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return an integer representing the date
	 */
	private int dateToInt(int year, int month, int day) {
		return year * 512 + month * 32 + day;
	}

	/**
	 * Return the date of the next day.
	 * 
	 * @param year  year of the current date
	 * @param month month of the current date
	 * @param day   day of the current date
	 * @return an array of String of length 3 where index 0, 1, 2 are the year,
	 *         month, day of the date of next day.
	 */
	private int[] nextDate(int year, int month, int day) {
		int[] nextDate = new int[3];

		if ((day + 1) > 31)
			if ((month + 1) > 12) {
				// year crossing
				year++;
				month = 1;
				day = 1;
			} else {
				// month crossing
				month++;
				day = 1;
			}
		else
			day++;

		nextDate[0] = year;
		nextDate[1] = month;
		nextDate[2] = day;

		return nextDate;
	}

	/**
	 * Compare 2 dates.
	 * 
	 * @param yStart
	 * @param mStart
	 * @param dStart
	 * @param yEnd
	 * @param mEnd
	 * @param dEnd
	 * @return true if the 2 dates are the same
	 */
	private boolean sameDate(int yStart, int mStart, int dStart, int yEnd, int mEnd,
			int dEnd) {
		return (dStart == dEnd) && (mStart == mEnd) && (yStart == yEnd);
	}

	/**
	 * Determine if the data of the year is available.
	 * 
	 * @param year the year to be found in the year list
	 * @return true the index of the annual report exists; -1 otherwise
	 */
	private annualData yearExists(int year) {
		for (annualData a : yearList)
			if (a.getYear() == year)
				return a;
		return null;
	}

	/**
	 * Calculate the yearly average of milk weight produced by this farm (i.e:
	 * [kg/day]).
	 * 
	 * @param year target year
	 * @return the average milk weight produced per day in this year
	 */
	public double annualAvg(int year) {
		annualData yearReport = yearExists(year);
		if (yearReport != null)
			return yearReport.getAnnualAverage(year);
		return 0;
	}

	/**
	 * Calculate annual sum of milk weight produced by this farm in the year
	 * specified.
	 * 
	 * @param year target year
	 * @return the total milk weight produced this year
	 */
	public double annualSum(int year) {
		annualData yearReport = yearExists(year);
		if (yearReport != null)
			return yearReport.getAnnualSum();
		return 0;
	}
	
	/**
	 * Calculate annual sum of milk weight produced by this farm in the month
	 * specified.
	 * 
	 * @param year target year
	 * @return the total milk weight produce this month
	 */
	public double monthlySum(int year, int month) {
		annualData yearReport = yearExists(year);
		if (yearReport != null)
			return yearReport.getMonthlySum(month);
		return 0;
	}
	
	/**
	 * Clear all data relating to the farm.
	 */
	public void clearData() {
		for (annualData a : yearList)
			a.clearAll();
	}

	/**
	 * Return the identifier of the farm.
	 * 
	 * @return the identifier of the farm
	 */
	public String getFarmID() {
		return farmID;
	}
}
