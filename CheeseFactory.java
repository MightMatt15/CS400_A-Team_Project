package application;


public class CheeseFactory {

  public BST<String, Farm> milkDataFromFarms;
  
  
  
  public CheeseFactory() {
    milkDataFromFarms = new BST<String, Farm>();
  }
  
  /*
   * Inserts a single data point on a given day
   * 
   * @param farmID unique ID of the farm
   * @param year year the date is in
   * @param month month the date to insert is in
   * @param day day to edit
   * @param weight weight of the milk for the day
   * @returns true if inserted successfully
   */
  public boolean insertSingleData(String farmID, int year, int month, int day, int weight) {
    try {
      //if farm doesnt exist, add it
      if(!milkDataFromFarms.contains(farmID)) {
        milkDataFromFarms.insert(farmID, new Farm(farmID));
      }
      milkDataFromFarms.get(farmID).insertMilkForDate(year, month, day, weight);
    } catch (IllegalNullKeyException | KeyNotFoundException | DuplicateKeyException e) {
      return false;
    }
    return true;
  }
  
  
  
  
  /*
   * Edits a single data point on a given day
   * 
   * @param farmID unique ID of the farm
   * @param year year the date is in
   * @param month month the date to edit is in
   * @param day day to edit
   * @param weight weight of the milk for the day to edit
   * @returns true if edited successfully
   */
  public boolean editSingleData(String farmID, int year, int month, int day, Integer weight) {
    try {
      milkDataFromFarms.get(farmID).editWeight(year, month, day, weight);
    } catch (IllegalNullKeyException | KeyNotFoundException e) {
      return false;
    }
    return true;
  }
  
  
  /*
   * remove a single data point on a given day
   * 
   * @param farmID unique ID of the farm
   * @param year year the date is in
   * @param month month the date to remove is in
   * @param day day to edit
   * @returns the data that was removed
   */
  public void removeSingleData(String farmID, int year, int month, int day) {
    try {
      milkDataFromFarms.get(farmID).removeMilkForDate(year, month, day);
    } catch (IllegalNullKeyException | KeyNotFoundException e) {
    }
  }
  
  
  
  /*
   * return a single data point on a given day
   * 
   * @param farmID unique ID of the farm
   * @param year year the date is in
   * @param month month the date to get is in
   * @param day day to edit
   * @returns the data that was retrieved or -2 if the farm was not found
   */
  public int getSingleData(String farmID, int year, int month, int day) {
    try {
      return milkDataFromFarms.get(farmID).getSingleData(year, month, day);
    } catch (IllegalNullKeyException | KeyNotFoundException e) {
      return -2;
    }
  }
}