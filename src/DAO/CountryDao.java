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
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.Time;
import static utilities.Time.stringToCalendar;

/**
 *
 * @author brown
 */
public class CountryDao {
    
    private static Connection connection = DBConnection.getConnection();
    
    public static ObservableList<Country> getAllCountries() { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM country;";
            DBQuery.ExecutePreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ResultSet result = ps.getResultSet();            
            Country userResult;
            ObservableList<Country> countries = FXCollections.observableArrayList();    
            while(result.next()){

                int countryId = result.getInt("countryId");;               
                String country = result.getString("country");
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");;               
                
                userResult = new Country(countryId, country, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                countries.add(userResult); //add everything to the table
                
            }                    
        return countries;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }  
    
    
    public static ObservableList<Country> getCountry(int Id) { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM country Where countryId = ?";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, Id);
            ps.execute();                
            
            ResultSet result = ps.getResultSet();            
            Country userResult;
            ObservableList<Country> countries = FXCollections.observableArrayList();    
            while(result.next()){

                int countryId = result.getInt("countryId");;               
                String country = result.getString("country");
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");;               
                
                userResult = new Country(countryId, country, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                countries.add(userResult); //add everything to the table
                
            }                    
        return countries;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }

    
// ----------------------------------------------------- get and set
    public static int getCountryId(String countryName)  {
        try{
            String sql = "SELECT countryId FROM country Where country = ?";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, countryName);
            ps.execute();                
            
            ResultSet result = ps.executeQuery();
            if(result.next() == false) {
                System.out.println("Country result is empty");                
            }
            else {                
                // here is the problem
                int countryId = result.getInt("countryId");            
                                    
            return countryId;
            }

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Going to return -1 for countryId");                    
        return -1;
    } 
    
    public static void setCountryId(String userName, String countryName)  {
        try{
            String sql = "INSERT INTO country (country, createDate, createdBy, lastUpdateBy)\n" +
                        "VALUES(?,?,?,?);";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();

            String currentDate = Time.currentUTCDate();

            ps.setString(1, countryName); 
            ps.setString(2, currentDate); // need to change this java.util.Date cannot be cast to java.sql.Date
            ps.setString(3, userName);
            ps.setString(4, userName);
            
System.out.println("in setCountryId.........");
            ps.executeUpdate(); // testing to see if this works
System.out.println("after execute setCountryId.........");
            

        }

        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    } 
    
    
    
    
    
}
