/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author brown
 */
public class TestController implements Initializable {

    /**
     * Initializes the controller class.
     */
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goback(javafx.event.ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view_controller/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


}


//        String updateStatement = "UPDATE country set country = ?, createdBy = ? WHERE country = ?";
//        
//        DBQuery.ExecutePreparedStatement(DBConnection.getConnection(), updateStatement); // create prepared statement
//        
//        PreparedStatement ps = DBQuery.getPreparedStatement();
//        
//        String countryName, newCountry, createdBy;
//        //String createDate = "2020-04-16 00:00:00";
//        //String createdBy = "admin";
//        //String lastUpdateBy = "admin";
//        
//        // Get keyboard input
//        Scanner keyboard = new Scanner(System.in);
//        
//        System.out.print("Type a country to update: ");        
//        countryName = keyboard.nextLine();
//        System.out.print("Enter a new country: ");        
//        newCountry = keyboard.nextLine();
//        System.out.print("Enter a user: ");        
//        createdBy = keyboard.nextLine();        
//
//        
//        // Key-Value mapping
//        //First is index of ? in the VALUES part... starts with index 1
//        ps.setString(1, newCountry);
//        ps.setString(2, createdBy);
//        ps.setString(3, countryName);
//        
//        // Execute prepared statement
//        ps.execute();
//        
//        // Check rows affected
//        
//        if(ps.getUpdateCount() > 0)
//            System.out.println(ps.getUpdateCount() + "rows affected");
//        else
//            System.out.println("no change...");