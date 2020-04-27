package application;

import java.util.ArrayList;

public class Farm {
	private String farmID; // farm ID can be any string
	private String owner;
	private ArrayList<annualData> yearList;

	/**
	 * Constructor to initialize a new farm
	 * 
	 * @param farmID farm identifier
	 * @param owner  owner of the farm
	 */
	public Farm(String farmID, String owner) {
		this.farmID = farmID;
		this.owner = owner;
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
		 * Default constructor.
		 */
		private annualData() {
			annualData = new int[12][31];
			monthlySum = new int[12];
		}

		/**
		 * Add weight to a date in this year.
		 * 
		 * @param month  month of date
		 * @param day    day of date
		 * @param weight amount of milk produced by this farm on this date of the year
		 */
		private void addData(int month, int day, int weight) {
			annualData[month - 1][day - 1] += weight;
			annualSum += weight;
			monthlySum[month - 1] += weight;
		}

		/**
		 * Return the amount of milk produced by this farm on this date of the year.
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
			annualData mdata = new annualData();
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

	private int[] nextDate(int year, int month, int day) {
		int[] nextDate = new int[3];

		if ((day + 1) > 31)
			if ((month + 1) > 12) {
				year++;
				month = 1;
				day = 1;
			} else {
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

	public boolean editMilkForDate() {

		return false;
	}

	public Object removeMilkForDate() {

		return null;
	}

	public Object clearData() {

		return null;
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
