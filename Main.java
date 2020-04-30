package application;

import java.io.IOException;
import javafx.stage.Stage;



public class Main {

  
  
  public static void main(String args[]) {
    //create and start the GUI
    GUI gui = new GUI();
    try {
      gui.start(new Stage());
      
      // testing:
      			// load 2019 January Data
			System.out.println(mng.readFile(
					"/Users/zlei/Documents/workspace_Eclipse/milkweight/application/2019-1.txt"));

			System.out.println("getMonthlyMin(2019): " + mng.getMonthlyMin(2019));
			System.out.println("getMonthlyMax(2019): " + mng.getMonthlyMax(2019));
			System.out.println("getMonthlyAvgVec(2019): "
					+ Arrays.toString(mng.getMonthlyAvgVec(2019)));
			System.out.println("getMonthlySumVec(2019): "
					+ Arrays.toString(mng.getMonthlySumVec(2019)));
			System.out.println("number of farms: " + mng.factory.getFactorySize());

			System.out.println(
					"Farm 0: Jan 18, 2019: " + mng.getSingleData("Farm 0", 2019, 1, 18));
			System.out.println(
					"Farm 1: Jan 18, 2019: " + mng.getSingleData("Farm 1", 2019, 1, 18));
			System.out.println(
					"Farm 2: Jan 18, 2019: " + mng.getSingleData("Farm 2", 2019, 1, 18));
			System.out.println(
					"Farm 0: Jan 22, 2019: " + mng.getSingleData("Farm 0", 2019, 1, 22));
			System.out.println(
					"Farm 1: Jan 22, 2019: " + mng.getSingleData("Farm 1", 2019, 1, 22));
			System.out.println(
					"Farm 2: Jan 22, 2019: " + mng.getSingleData("Farm 2", 2019, 1, 22));

			System.out.println(
					"Farm 0: Feb 1, 2019: " + mng.getSingleData("Farm 0", 2019, 2, 1));
			System.out.println(
					"Farm 1: Feb 1, 2019: " + mng.getSingleData("Farm 1", 2019, 2, 1));
			System.out.println(
					"Farm 2: Feb 1, 2019: " + mng.getSingleData("Farm 2", 2019, 2, 1));

			// load 2019 February Data
			System.out.println(mng.readFile(
					"/Users/zlei/Documents/workspace_Eclipse/milkweight/application/2019-2.txt"));

			System.out.println("getMonthlyMin(2019): " + mng.getMonthlyMin(2019));
			System.out.println("getMonthlyMax(2019): " + mng.getMonthlyMax(2019));
			System.out.println("getMonthlyAvgVec(2019): "
					+ Arrays.toString(mng.getMonthlyAvgVec(2019)));
			System.out.println("getMonthlySumVec(2019): "
					+ Arrays.toString(mng.getMonthlySumVec(2019)));

			// 2019 January 10:20 //
			System.out.println("2019 Farm 0: Jan 10:20 [Sum, time span(days)]: "
					+ Arrays.toString(mng.factory.milkDataFromFarms.get(0)
							.sumWithinRange(2019, 1, 10, 2019, 1, 20)));

			System.out.println("2019 Farm 1: Jan 10:20 [Sum, time span(days)]: "
					+ Arrays.toString(mng.factory.milkDataFromFarms.get(1)
							.sumWithinRange(2019, 1, 10, 2019, 1, 20)));

			System.out.println("2019 Farm 2: Jan 10:20 [Sum, time span(days)]: "
					+ Arrays.toString(mng.factory.milkDataFromFarms.get(2)
							.sumWithinRange(2019, 1, 10, 2019, 1, 20)));

			// 2019 January 25 : February 5 //
			System.out.println("2019 Farm 0: Jan 25: Feb 5: [Sum, time span(days)]: "
					+ Arrays.toString(mng.factory.milkDataFromFarms.get(0)
							.sumWithinRange(2019, 1, 25, 2019, 2, 5)));

			System.out.println("2019 Farm 1: Jan 25: Feb 5: [Sum, time span(days)]: "
					+ Arrays.toString(mng.factory.milkDataFromFarms.get(1)
							.sumWithinRange(2019, 1, 25, 2019, 2, 5)));

			System.out.println("2019 Farm 2: Jan 25: Feb 5: [Sum, time span(days)]: "
					+ Arrays.toString(mng.factory.milkDataFromFarms.get(2)
							.sumWithinRange(2019, 1, 25, 2019, 2, 5)));

			// test percentages //
			System.out.println("Farm 0 percentage 2019 all year: "
					+ mng.percentageVectorYear("Farm 0", 2019));
			System.out.println("Farm 1 percentage 2019 all year: "
					+ mng.percentageVectorYear("Farm 1", 2019));
			System.out.println("Farm 2 percentage 2019 all year: "
					+ mng.percentageVectorYear("Farm 2", 2019));

			System.out.println("Farm 0 percentage 2019 by month: "
					+ Arrays.toString(mng.percentageVectorMonth("Farm 0", 2019)));
			System.out.println("Farm 1 percentage 2019 by month: "
					+ Arrays.toString(mng.percentageVectorMonth("Farm 1", 2019)));
			System.out.println("Farm 2 percentage 2019 by month: "
					+ Arrays.toString(mng.percentageVectorMonth("Farm 2", 2019)));
			
			System.out.println("getPercentageInRange(2019, Jan. percentage): "
					+ Arrays.toString(mng.getPercentageInRange(2019, 1, 1, 2019, 1, 31)));
			
			System.out.println("getPercentageInRange(2019, Feb. percentage): "
					+ Arrays.toString(mng.getPercentageInRange(2019, 2, 1, 2019, 2, 28)));
			
			System.out.println("getPercentageInRange(2019, whole year percentage): "
					+ Arrays.toString(mng.getPercentageInRange(2019, 1, 1, 2019, 12, 31)));

			System.out.println("2019.01.01 : 2019.12.31 [farm 0 , farm 1, farm 2]: "
					+ Arrays.toString(
							mng.getSumInRangeEachFarm(2019, 1, 1, 2019, 12, 31)));
			System.out.println(
					"2019 farm 0: " + mng.factory.getFarm("Farm 0").annualSum(2019));
			System.out.println(
					"2019 farm 1: " + mng.factory.getFarm("Farm 1").annualSum(2019));
			System.out.println(
					"2019 farm 2: " + mng.factory.getFarm("Farm 2").annualSum(2019));
			System.out.println("2019.01.01 : 2019.12.31 all farms:  "
					+ mng.getSumInRangeAllFarms(2019, 1, 1, 2019, 12, 31));

			System.out.println("getMonthlySumForFarm(Farm 0, 2019): "
					+ Arrays.toString(mng.getMonthlySumForFarm("Farm 0", 2019)));

			System.out.println("getMonthlySumForFarm(Farm 1, 2019): "
					+ Arrays.toString(mng.getMonthlySumForFarm("Farm 1", 2019)));

			System.out.println("getMonthlySumForFarm(Farm 2, 2019): "
					+ Arrays.toString(mng.getMonthlySumForFarm("Farm 2", 2019)));
      
    } catch (Exception e) {
      System.out.println("GUI failed to start");
    }
    
    
    
    
  }
}
