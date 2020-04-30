package application;

import java.util.ArrayList;

public class CheeseFactory {

	private ArrayList<Farm> milkDataFromFarms;

	public CheeseFactory() {
		milkDataFromFarms = new ArrayList<Farm>();
	}

	
	public ArrayList<Farm> getFarmList(){
		return milkDataFromFarms;
	}
	
		public int annualSumAllFarms(int year) {
		int annualSum = 0;
		for(Farm f : milkDataFromFarms) {
			annualSum += f.annualSum(year);
		}
		return annualSum;
	}
	
	/**
	 * Inserts a single data point on a given day
	 * 
	 * @param farmID unique ID of the farm
	 * @param year   year the date is in
	 * @param month  month the date to insert is in
	 * @param day    day to edit
	 * @param weight weight of the milk for the day
	 */
	public void insertSingleData(String farmID, int year, int month, int day,
			int weight) {
		// if farm does not exist, add it
		Farm farm = getFarm(farmID);
		if (farm == null) {
			milkDataFromFarms.add(new Farm(farmID));
		}
	    farm = getFarm(farmID);
		farm.insertMilkForDate(year, month, day, weight);
	}

	/**
	 * Get the farm specified by farmID.
	 * 
	 * @param farmID farm identifier
	 * @return farm reference; null if farm does not exist;
	 */
	private Farm getFarm(String farmID) {
		for (Farm farm : milkDataFromFarms) {
			if (farm.getFarmID().equals(farmID))
				return farm;
		}
		return null;
	}

	/**
	 * Get the number of farms in the cheese factory.
	 * 
	 * @return the number of farms in the factory
	 */
	public int getFactorySize() {
		return milkDataFromFarms.size();
	}

	/**
	 * Edits a single data point on a given day
	 * 
	 * @param farmID unique ID of the farm
	 * @param year   year the date is in
	 * @param month  month the date to edit is in
	 * @param day    day to edit
	 * @param weight weight of the milk for the day to edit
	 * @returns true if edited successfully
	 */
	public boolean editSingleData(String farmID, int year, int month, int day,
			Integer weight) {
		Farm farm = getFarm(farmID);
		if (farm == null)
			return false;
		else {
			farm.editWeight(year, month, day, weight);
			return true;
		}
	}

	/**
	 * Remove a single data point on a given day
	 * 
	 * @param farmID unique ID of the farm
	 * @param year   year the date is in
	 * @param month  month the date to remove is in
	 * @param day    day to edit
	 * @returns the data that was removed; -1 if removal unsuccessful
	 */
	public int removeSingleData(String farmID, int year, int month, int day) {
		Farm farm = getFarm(farmID);
		if (farm == null)
			return -1;
		else
			return farm.removeMilkForDate(year, month, day);
	}

	/**
	 * Return a single data point on a given day
	 * 
	 * @param farmID unique ID of the farm
	 * @param year   year the date is in
	 * @param month  month the date to get is in
	 * @param day    day to edit
	 * @returns the data that was retrieved or -1 if the farm was not found
	 */
	public int getSingleData(String farmID, int year, int month, int day) {
		Farm farm = getFarm(farmID);
		if (farm == null)
			return -1;
		else
			return farm.getSingleData(year, month, day);
	}
}
