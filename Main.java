package application;

import java.io.IOException;
import javafx.stage.Stage;



public class Main {

  
  
  public static void main(String args[]) {
    //create and start the GUI
    GUI gui = new GUI();
    try {
      gui.start(new Stage());
    } catch (Exception e) {
      System.out.println("GUI failed to start");
    }
    
    
    
  }
}
