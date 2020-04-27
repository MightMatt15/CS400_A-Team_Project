package application;


public class Farm {
  
  public String farmID;
  public String owner;
  public BST<String, Data> milkData;           //TODO data structure
  
  
  //constructor to initialize a new farm
  public Farm(String farmID, String owner) {
    this.farmID = farmID;
    this.owner = owner;
  }
  
  /*
   * A class to store a year's worth of data
   */
  private class Data{
    private Integer january[];
    private Integer february[];
    private Integer march[];
    private Integer april[];
    private Integer may[];
    private Integer june[];
    private Integer july[];
    private Integer august[];
    private Integer september[];
    private Integer october[];
    private Integer november[];
    private Integer december[];
    
    private Data() {
      january = new Integer[31];
      february = new Integer[29];
      march = new Integer[31];
      april = new Integer[30];
      may = new Integer[31];
      june = new Integer[30];
      july = new Integer[31];
      august = new Integer[31];
      september = new Integer[30];
      october = new Integer[31];
      november = new Integer[30];
      december = new Integer[31];
    }
  }
  
  /*
   * Inserts data for milk on a specific date
   * 
   * @throws ArrayIndexOutOfBoundsException if the day to access is invalid
   * 
   * @param year year the date is in
   * @param month month the date to edit is in
   * @param day day to edit
   * @param weight weight of the milk for the day
   * @returns true if inserted successfully
   * 
   */
  public boolean insertMilkForDate(int year, String month, int day, Integer weight) throws ArrayIndexOutOfBoundsException {
    try {
      
      //TODO check if day/month/year is already in tree???
      //cant just add a new year if it is there already
      //maybe start by adding a year if it is found in the file and init to 0
      //then check if year exists and check if day is nonzero??
      //could use a method to find the day in the tree and edit it?
      
      milkData.insert(farmID, data);
    } catch (IllegalNullKeyException e) {
      return false;
    } catch (DuplicateKeyException e) {
      return false;
    }
    
    return true;
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
  
  
  
  
  
  
  
}
