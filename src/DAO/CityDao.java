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
import model.City;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.Time;
import static utilities.Time.stringToCalendar;

/**
 *
 * @author brown
 */
public class CityDao {
    
    private static Connection connection = DBConnection.getConnection();
    
    public static ObservableList<City> getAllCities() { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM city;";
            DBQuery.ExecutePreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ResultSet result = ps.getResultSet();            
            City userResult;
            ObservableList<City> cities = FXCollections.observableArrayList();    
            while(result.next()){

                int cityId = result.getInt("cityId");               
                String city = result.getString("city");
                int countryId = result.getInt("countryId");
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");               
                
                userResult = new City(cityId, city, countryId, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                cities.add(userResult); //add everything to the table
                
            }                    
        return cities;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }
    
    public static ObservableList<City> getCity(int Id) { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM city Where cityId = ?";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, Id);
            ps.execute();            
            ResultSet result = ps.getResultSet();            
            City userResult;
            ObservableList<City> cities = FXCollections.observableArrayList();    
            while(result.next()){

                int cityId = result.getInt("cityId");               
                String city = result.getString("city");
                int countryId = result.getInt("countryId");
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");               
                
                userResult = new City(cityId, city, countryId, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                cities.add(userResult); //add everything to the table
                
            }                    
        return cities;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    } 
    
    
// ----------------------------------------------------- get and set
    public static int getCityId(String cityName, int countryId)  {
        try{
            String sql = "SELECT cityId FROM city \n" +
                        "Where city = ? AND countryId = ?;";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, cityName);
            ps.setInt(2, countryId);
            ps.execute();                
            
            ResultSet result = ps.getResultSet();            
            if(result.next() == false) {
                System.out.println("City ResultSet is empty");                
            }
            else {
                int cityId = result.getInt("cityId");            
                System.out.println("After result set....." + cityId);

                return cityId;
            }

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Going to return -1 for cityId");                                
        return -1;
    }
    
    public static void setCityId(String userName, String cityName, int countryId)  {
        try{
            String sql = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdateBy)\n" +
                        "VALUES(?,?,?,?,?);";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();

            String currentDate = Time.currentUTCDate();

            ps.setString(1, cityName); 
            ps.setInt(2, countryId);
            ps.setString(3, currentDate);
            ps.setString(4, userName);
            ps.setString(5, userName);

System.out.println("in setCityId.........");            
            ps.execute();                
System.out.println("after execute setCityId.........");

        }

        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    } 
    
    
    
}
