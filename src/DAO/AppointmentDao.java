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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.Time;
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
                String end = result.getString("end");
                Calendar endDateCalendar=stringToCalendar(end);
                
                

                LocalDateTime startLDT = Time.stringToLocalDateTime(start);
                LocalDateTime endLDT = Time.stringToLocalDateTime(end);
                
                
                                
                
                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, startLDT, endLDT, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
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
            
            String sql = "SELECT a.appointmentId, u.userId, u.userName, c.customerId, c.customerName,\n" +
                        " a.title, a.description, a.location,a.contact, a.type, a.url, a.start, a.end,\n" +
                        " a.createDate, a.createdBy, a.lastUpdate, a.lastUpdateBy\n" +
                        "FROM appointment a\n" +
                        "INNER JOIN customer c ON a.customerId = c.customerId\n" +
                        "INNER JOIN user u ON a.userId = u.userId\n" +
                        "WHERE u.userId = ? ;";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setInt(1, uId);
            
            ps.execute();
            
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
                String end = result.getString("end");
                Calendar endDateCalendar=stringToCalendar(end);


                LocalDateTime startLDT = Time.stringToLocalDateTime(start);
                LocalDateTime endLDT = Time.stringToLocalDateTime(end);
                
                
                
                                
                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Appointment(appointmentId, customerId, customerName, userId, userName, title, description, location, contact, type, url, startLDT, endLDT, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                appointments.add(userResult); //add everything to the table
                
            }                    
        return appointments;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }
   
   
      public static ObservableList<Appointment> getWeekAppointmentsJoined(int uId) { //Gets info for loggin in
        
        try{
            System.out.println("AppointmentDao :" + uId);
            
            
            String sql = "SELECT a.appointmentId, u.userId, u.userName, c.customerId, c.customerName,\n" +
                        " a.title, a.description, a.location,a.contact, a.type, a.url, a.start, a.end,\n" +
                        " a.createDate, a.createdBy, a.lastUpdate, a.lastUpdateBy\n" +
                        "FROM appointment a\n" +
                        "INNER JOIN customer c ON a.customerId = c.customerId\n" +
                        "INNER JOIN user u ON a.userId = u.userId\n" +
                        "WHERE u.userId = ? AND start <= ?;";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setInt(1, uId);
            //get a date a week out from today
            LocalDateTime nextWeek = Time.getNextWeek();            
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            ps.setString(2, nextWeek.format(customFormatter));
            ps.execute();
            
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
                String end = result.getString("end");
                Calendar endDateCalendar=stringToCalendar(end);
                
                
                

                LocalDateTime startLDT = Time.stringToLocalDateTime(start);
                LocalDateTime endLDT = Time.stringToLocalDateTime(end);
                
                
                                
                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Appointment(appointmentId, customerId, customerName, userId, userName, title, description, location, contact, type, url, startLDT, endLDT, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                appointments.add(userResult); //add everything to the table
                
            }                    
        return appointments;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }
      
      
      
     public static ObservableList<Appointment> getMonthAppointmentsJoined(int uId) { //Gets info for loggin in
        
        try{
            System.out.println("AppointmentDao :" + uId);
            
            
            String sql = "SELECT a.appointmentId, u.userId, u.userName, c.customerId, c.customerName,\n" +
                        " a.title, a.description, a.location,a.contact, a.type, a.url, a.start, a.end,\n" +
                        " a.createDate, a.createdBy, a.lastUpdate, a.lastUpdateBy\n" +
                        "FROM appointment a\n" +
                        "INNER JOIN customer c ON a.customerId = c.customerId\n" +
                        "INNER JOIN user u ON a.userId = u.userId\n" +
                        "WHERE u.userId = ? AND start <= ?;";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setInt(1, uId);
            //get a date a week out from today
            LocalDateTime nextMonth = Time.getNextMonth();            
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            ps.setString(2, nextMonth.format(customFormatter));
            ps.execute();
            
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
                String end = result.getString("end");
                Calendar endDateCalendar=stringToCalendar(end);
                
                

                LocalDateTime startLDT = Time.stringToLocalDateTime(start);
                LocalDateTime endLDT = Time.stringToLocalDateTime(end);
                
                
                                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Appointment(appointmentId, customerId, customerName, userId, userName, title, description, location, contact, type, url, startLDT, endLDT, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                appointments.add(userResult); //add everything to the table
                
            }                    
        return appointments;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }      
   
   
   // -------------------------- Set appointment ------------------------------
   
   public static void setAppointment(int customerId, int userId, String title, String description, String location, String contact, String type, String url, String start, String end) {
   // customerId, userId, title, description, location, contact, type, url, start, end 
        try{

            String sql = "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            String currentDate = Time.currentUTCDate();
            
            String userName = UserDao.getUserName(userId);


            ps.setInt(1, customerId);
            ps.setInt(2, userId);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, location);
            ps.setString(6, contact);
            ps.setString(7, type);
            ps.setString(8, url);
            ps.setString(9, start);
            ps.setString(10, end);
            ps.setString(11, currentDate);
            ps.setString(12, userName);
            ps.setString(13, userName);
            
            ps.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }
   
   }

    public static void updateAppointment(int selectedAppointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, String start, String end) {

        try{

            String sql = "UPDATE appointment\n" +
                         "SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = ?, lastUpdateBy = ?" +
                         "WHERE appointmentId = ?;";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            String currentDate = Time.currentUTCDate();
            
            String userName = UserDao.getUserName(userId);


            ps.setInt(1, customerId);
            ps.setInt(2, userId);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, location);
            ps.setString(6, contact);
            ps.setString(7, type);
            ps.setString(8, url);
            ps.setString(9, start);
            ps.setString(10, end);
            ps.setString(11, currentDate);
            ps.setString(12, userName);
            ps.setInt(13, selectedAppointmentId);
            
            ps.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }        
        

    }

    public static void removeAppointmentsWithCustomerId(int customerId) {

        try{

            String sql = "DELETE FROM appointment WHERE customerId = ?;";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setInt(1, customerId);
            
            ps.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }           
        
        
    }

    public static void deleteAppointment(int appointmentId) {
        
        try{

            String sql = "DELETE FROM appointment WHERE appointmentId = ?;";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setInt(1, appointmentId);
            
            ps.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }           
    }
   
    
// -------------------------------------

// any time the appointment table is set - for(int i=0; i<allAppointments.size(); i++) {
//                                              appointment = allAppointments.get(i)
//                                              startTime = getAppointmentStartTime(appointment.getUserId) 
//                                              if(startTime is within 15 minutes of current time)
//                                                  meeting1 = current time - start time
//                                                  if (meeting1 < meeting2) // if meeting2 starts earlier
//                                                          meeting = meeting2
//                                              print meeting starts in ___ minutes
//    
        public static long getNextAppointmentStartsIn(int userId) {
        
        ArrayList<Long> minutesUntilMeetings = new ArrayList<>();            
            
        try{
            String sql = "SELECT start FROM appointment WHERE userId = ?;";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setInt(1, userId);
                        
            ResultSet result = ps.executeQuery();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
            int num = 0;
            while(result.next()) {
                String startString = result.getString("start");
                LocalDateTime start = LocalDateTime.parse(startString, formatter);                                
                LocalDateTime currentTime = LocalDateTime.now();
                long timeDifference = ChronoUnit.MINUTES.between(currentTime, start);                                
                minutesUntilMeetings.add(timeDifference);
                num = num + 1;                
            }
        }       
        catch(Exception e) {
            System.out.println(e.getMessage());
        } 
        
            long smallest = Long.MAX_VALUE;
            for(int i=0; i<minutesUntilMeetings.size(); i++) {
               if(minutesUntilMeetings.get(i) > 0 && minutesUntilMeetings.get(i) < smallest){
                   smallest = minutesUntilMeetings.get(i);
               } 
            }
            return smallest;
    }
    
}
