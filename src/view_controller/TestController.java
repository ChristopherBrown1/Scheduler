/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import DAO.AppointmentDao;
import DAO.CustomerDao;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Appointment;


/**
 * FXML Controller class
 *
 * @author brown
 */
public class TestController implements Initializable {

    private int currentUserId;
    private String userName;
    @FXML
    private TextArea textBox;

    public TestController(int uId, String userName) {
        currentUserId = uId;
        this.userName = userName;

    }    
    
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_controller/MainScreen.fxml")); // loads our main screen.
        view_controller.MainScreenController controller = new view_controller.MainScreenController(currentUserId, userName);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();            
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void appointmentsByMonth(javafx.scene.input.MouseEvent event) {
        //puts the number of appointments per type on the textBox this is for a specific user of the app.
        textBox.clear();
        textBox.setText("NUMBER OF APPOINTMENTS BY TYPE\n\n");
        
        HashMap<String, Integer> hash_map = new HashMap<String, Integer>();       
        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointmentsJoined(currentUserId);
        for(int i = 0; i<allAppointments.size(); i++){            
            Appointment appointment = allAppointments.get(i);
            String appointmentType = appointment.getType();
            
            hash_map.putIfAbsent(appointmentType, 0);
            hash_map.put(appointmentType, hash_map.get(appointmentType) + 1);
        }
        
        hash_map.forEach((k,v) -> textBox.appendText((k+": "+v+"\n")));        
    }

    @FXML
    private void consultantSchedules(javafx.scene.input.MouseEvent event) {
        // prints all of the customers schedules to the text box
        textBox.clear();
        textBox.setText("Customer Schedules\n\n");
        
        HashMap<String, String> hash_map = new HashMap<String, String>();       
        ObservableList<Appointment> allAppointments = AppointmentDao.getAllAppointmentsJoined(currentUserId);
        HashMap<String, Integer> numOfAppointments = new HashMap<String, Integer>();
        for(int i = 0; i<allAppointments.size(); i++){            
            Appointment appointment = allAppointments.get(i);
            String customerName = appointment.getCustomerName();
            String appointmentStart = appointment.getStart().toString();  
            String appointmentEnd = appointment.getEnd().toString();
            
            if(!hash_map.containsKey(customerName)){
                numOfAppointments.putIfAbsent(customerName, 1);
                hash_map.putIfAbsent(customerName, "\n"+numOfAppointments.get(customerName)+":   Start) "+appointmentStart+"   End) "+appointmentEnd +"\n");
            }
            else{
                numOfAppointments.put(customerName, numOfAppointments.get(customerName)+1);
                hash_map.put(customerName, hash_map.get(customerName)+numOfAppointments.get(customerName)+":   Start) "+appointmentStart+"   End) "+appointmentEnd +"\n");
            }            
        }
        
        hash_map.forEach((k,v) -> textBox.appendText((k+":  "+v+"\n\n")));        
    }

    @FXML
    private void allPhoneNumbers(javafx.scene.input.MouseEvent event) {
        // prints all of the names with phone numbers to the text box
        textBox.clear();
        textBox.setText("ALL CUSTOMER NAMES AND PHONE NUMBERS\n\n" + CustomerDao.getCustomerNamesAndPhone());
    }


}

