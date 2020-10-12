package vu8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nam Vu
 * Assignment 5
 * Program Cookie Inventory
 * Date: August 8th 2020
 * 
 * Other Files in this project:
 *      CookieInventoryFile.java
 *      FXMLCookieInventory.fxml
 *      FXMLCookieInventoryController.java
 *      CookieInventoryItem.class
 *      Cookies.class
 * 
 * Main class: Assign5.java
 */
public class Assign5 extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLCookieInventory.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cookie Inventory");
        stage.show();
    }
    
}
