/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.UserDao.isActive;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utilities.DBConnection;
import utilities.DBQuery;
import static utilities.Time.stringToCalendar;

/**
 *
 * @author brown
 */
public class AppointmentDao {
    
    private static Connection connection = DBConnection.getConnection();
    
    public static ObservableList<Appointment> getAllAppointments() { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM appointment;";
            DBQuery.ExecutePreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ResultSet result = ps.getResultSet();            
            Appointment userResult;
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();    
            while(result.next()){

                int appointmentId = result.getInt("appointmentId");               
                int customerId = result.getInt("customerId");
                int userId = result.getInt("userId");                               
                String title = result.getString("title");
                String description = result.getString("description");
                String location = result.getString("location");
                String contact = result.getString("contact");
                String type = result.getString("type");
                String url = result.getString("url");                
                String start = result.getString("start");
                Calendar startDateCalendar=stringToCalendar(start);
                String end = result.getString("createDate");
                Calendar endDateCalendar=stringToCalendar(end);                                
                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                appointments.add(userResult); //add everything to the table
                
            }                    
        return appointments;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }

    // This returns the customerName and userName in addition to all appointment fields.
   public static ObservableList<Appointment> getAllAppointmentsJoined(int uId) { //Gets info for loggin in
        
        try{
            System.out.println("AppointmentDao :" + uId);
            
            
            String sql = "SELECT a.appointmentId, u.userId, u.userName, c.customerId, c.customerName,\n" +
                        " a.title, a.description, a.location,a.contact, a.type, a.url, a.start, a.end,\n" +
                        " a.createDate, a.createdBy, a.lastUpdate, a.lastUpdateBy\n" +
                        "FROM appointment a\n" +
                        "INNER JOIN customer c ON a.customerId = c.customerId\n" +
                        "INNER JOIN user u ON a.userId = u.userId\n" +
                        "WHERE u.userId = " + uId + ";";
            DBQuery.ExecutePreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ResultSet result = ps.getResultSet();            
            Appointment userResult;
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();    
            while(result.next()){

                int appointmentId = result.getInt("appointmentId");               
                int customerId = result.getInt("customerId");
                String customerName = result.getString("customerName");
                int userId = result.getInt("userId");
                String userName = result.getString("userName");
                String title = result.getString("title");
                String description = result.getString("description");
                String location = result.getString("location");
                String contact = result.getString("contact");
                String type = result.getString("type");
                String url = result.getString("url");                
                String start = result.getString("start");
                Calendar startDateCalendar=stringToCalendar(start);
                String end = result.getString("createDate");
                Calendar endDateCalendar=stringToCalendar(end);                                
                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Appointment(appointmentId, customerId, customerName, userId, userName, title, description, location, contact, type, url, startDateCalendar, endDateCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                appointments.add(userResult); //add everything to the table
                
            }                    
        return appointments;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }       
    
    
    
}
