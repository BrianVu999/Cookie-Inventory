/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vu8;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import prog24178.labs.objects.CookieInventoryItem;
import prog24178.labs.objects.Cookies;

/**
 * FXML Controller class
 *
 * @author Nam Vu
 */
public class FXMLCookieInventoryController implements Initializable {

    @FXML
    private ComboBox ddlFlavour;
    @FXML
    private TextField txtQuantitySold, txtQuantityBaked;
    private Cookies selectedCookie;
    private CookieInventoryFile cookieDatabase = new CookieInventoryFile(new File("cookies.txt"));

    /**
     * Initializes the controller class.
     */
    
    // Display alert message
    public void alertIfError(String alertMessage) {
        if (!alertMessage.isEmpty()) {
            Alert alertSell = new Alert(AlertType.ERROR, alertMessage,
                    ButtonType.OK);
            alertSell.setTitle("Data Entry Error");
            alertSell.showAndWait();
        }
    }
    public void Sell() {
        String alertMessage = "";
        
        //Check selected flavour; numeric and empty text field
        boolean isCookieSelected = true;
        if (selectedCookie == null) {
            alertMessage += "Please select cookie flavour.";
            isCookieSelected = false;
        }
        int sellQty = 0;
        boolean isNumericAndNotEmpty = true;
        if (isCookieSelected) {
            try {
                sellQty = Integer.parseInt(txtQuantitySold.getText());
            } catch (NumberFormatException e) {
                if (txtQuantitySold.getText().isEmpty()) {
                    alertMessage += "Please enter the number of cookies sold";
                } else {
                    alertMessage += "You must enter a valid numeric value.";
                }
                isNumericAndNotEmpty = false;
            }
        }

        //Check different situation of data input
        if (isNumericAndNotEmpty && isCookieSelected) {
            if (sellQty <= 0) {
                alertMessage += "You must enter a quantity that is greater than 0.";
            } else if (cookieDatabase.find(selectedCookie.getId()) != null) {
                CookieInventoryItem selectedCookieFromDB = cookieDatabase.find(selectedCookie.getId());
                if (selectedCookieFromDB.getQuantity() == sellQty) {
                    cookieDatabase.remove(selectedCookieFromDB);
                } else if (selectedCookieFromDB.getQuantity() > sellQty) {
                    cookieDatabase.remove(selectedCookieFromDB);
                    cookieDatabase.add(new CookieInventoryItem(selectedCookie, selectedCookieFromDB.getQuantity() - sellQty));
                } else if (selectedCookieFromDB.getQuantity() < sellQty) {
                    alertMessage += "Sorry, not enough " + selectedCookie + " cookies to sell."
                            + "\nYou only have " + selectedCookieFromDB.getQuantity();
                }
            } else {
                alertMessage += "Sorry, there are no " + selectedCookie + " cookies available to sell.";
            }
        }

        this.alertIfError(alertMessage);
    }

    public void Add() {
        String alertMessage = "";

        //Check selected flavour; numeric and empty text field
        boolean isCookieSelected = true;
        if (selectedCookie == null) {
            alertMessage += "Please select cookie flavour.";
            isCookieSelected = false;
        }
        int bakedQty = 0;
        boolean isNumericAndNotEmpty = true;
        if (isCookieSelected) {
            try {
                bakedQty = Integer.parseInt(txtQuantityBaked.getText());
            } catch (NumberFormatException e) {
                if (txtQuantityBaked.getText().isEmpty()) {
                    alertMessage += "Please enter the number of cookies baked";
                } else {
                    alertMessage += "You must enter a valid numeric value.";
                }
                isNumericAndNotEmpty = false;
            }
        }

        //Check different situation of data input
        if (isNumericAndNotEmpty && isCookieSelected) {
            if (bakedQty <= 0) {
                alertMessage += "You must enter a quantity that is greater than 0.";
            } else {
                CookieInventoryItem selectedCookieFromDB = cookieDatabase.find(selectedCookie.getId());
                if (selectedCookieFromDB==null) {
                    cookieDatabase.add(new CookieInventoryItem(selectedCookie.getId(), bakedQty));
                } else {
                    cookieDatabase.remove(selectedCookieFromDB);
                    cookieDatabase.add(new CookieInventoryItem(selectedCookie.getId(), bakedQty + selectedCookieFromDB.getQuantity()));
                }
            }
        }

        this.alertIfError(alertMessage);
    }

    public void Exit() {
        Alert alertExit = new Alert(AlertType.CONFIRMATION, "Are you sure you wish to exit",
                ButtonType.YES, ButtonType.NO);
        alertExit.setTitle("Exit Program");
        Optional<ButtonType> result = alertExit.showAndWait();
        if (result.get() == ButtonType.YES) {
            cookieDatabase.writeToFile(new File("cookies.txt"));
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Add objects to comboBox
        ObservableList<Cookies> obsCookieList = FXCollections.observableArrayList();
        for (Cookies cookie : Cookies.values()) {
            obsCookieList.add(cookie);
        }
        ddlFlavour.setItems(obsCookieList);
        
        //Update selected Item 
        ddlFlavour.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                try {
                    selectedCookie = (Cookies) ddlFlavour.getSelectionModel().getSelectedItem();
                } catch (Exception e) {
                    //If the user has not selected item yet.
                }
            }
        });

    }

}
