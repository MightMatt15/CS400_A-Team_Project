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

	private class annualData {
		private int year;
		private int annualSum;
		private int[][] annualData;
		private int[] monthlySum;

		private annualData() {
			annualData = new int[12][31];
			monthlySum = new int[12];
		}

		private void addData(int month, int day, int weight) {
			annualData[month - 1][day - 1] += weight;
			annualSum += weight;
			monthlySum[month - 1] += weight;
		}

		private int getData(int month, int day) {
			return annualData[month - 1][day - 1];
		}

		private int getAnnualSum() {
			return annualSum;
		}

		private int getMonthlySum(int month) {
			return monthlySum[month - 1];
		}

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

	public int sumWithinRange(int year1, int month1, int day1, int year2, int month2,
			int day2) {
		int sum = 0;
		int[] nextDate;
		annualData year = null;
		// date 1 is the start date, date 2 is the end date
		while (dateToInt(year1, month1, day1) <= dateToInt(year2, month2, day2)) {
			year = yearExists(year1);
			if (year == null) { // year does not exist; go to the next year
				year1++;
				month1 = 1;
				day1 = 1;
				continue;
			}
			sum += year.getData(month1, day1);
			nextDate = nextDate(year1, month1, day1); // increment the date
			year1 = nextDate[0];
			month1 = nextDate[1];
			day1 = nextDate[2];
		}
		return sum;
	}

	private int dateToInt(int year, int month, int day) {
		return year * 10000 + month * 100 + day;
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

	private boolean sameDate(int year1, int month1, int day1, int year2, int month2,
			int day2) {
		return (day1 == day2) && (month1 == month2) && (year1 == year2);
	}

	/**
	 * Determine if the annual report has been created.
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
