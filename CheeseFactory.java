package application;


public class CheeseFactory {

  public String name;
  public BST<String, Farm> milkDataFromFarms;              //TODO data structure
  
  
  public CheeseFactory(String name) {
    this.name = name;
    milkDataFromFarms = new BST<String, Farm>();
  }
  
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
      milkDataFromFarms.get(farmID).insertMilkForDate(year, month, day, weight);
    } catch (IllegalNullKeyException | KeyNotFoundException e) {
      return false;
    }
    return true;
  }
  
  
  
  
//  public boolean insertData(String farmID, int year, int month) {
//    
//    return false;
//  }
  
  
  
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
  public boolean editSingleData(String farmID, int year, String month, int day, Integer weight) {
    try {
      milkDataFromFarms.get(farmID).editMilkForDate(year, month, day, weight);
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
  public Integer removeSingleData(String farmID, int year, String month, int day) {
    try {
      return milkDataFromFarms.get(farmID).removeMilkForDate(year, month, day);
    } catch (IllegalNullKeyException | KeyNotFoundException e) {
      return -1;
    }
    return -1;
  }
}