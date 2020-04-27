/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Address;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.Time;
import static utilities.Time.stringToCalendar;

/**
 *
 * @author brown
 */
public class AddressDao {
    
    private static Connection connection = DBConnection.getConnection();
    
    public static ObservableList<Address> getAllAddresses() { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM address;";
            DBQuery.ExecutePreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ResultSet result = ps.getResultSet();            
            Address userResult;
            ObservableList<Address> addresses = FXCollections.observableArrayList();    
            while(result.next()){

                int addressId = result.getInt("addressId");                                             
                String address = result.getString("address");
                String address2 = result.getString("address2");
                int cityId = result.getInt("cityId");
                String postalCode = result.getString("postalCode");
                String phone = result.getString("phone");                                                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Address(addressId, address, address2, cityId, postalCode, phone, createDateCalendar, createdBy,lastUpdateCalendar, lastUpdateBy); //get the user info
                addresses.add(userResult); //add everything to the table
                
            }                    
        return addresses;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }
    
    
    public static ObservableList<Address> getAddress(int Id) { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM address Where addressId = ?";            
            DBQuery.SetPreparedStatement(connection, sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, Id);
            ps.execute();
            ResultSet result = ps.getResultSet();            
            Address userResult;
            ObservableList<Address> addresses = FXCollections.observableArrayList();    
            while(result.next()){

                int addressId = result.getInt("addressId");                                             
                String address = result.getString("address");
                String address2 = result.getString("address2");
                int cityId = result.getInt("cityId");
                String postalCode = result.getString("postalCode");
                String phone = result.getString("phone");                                                
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");              
                
                userResult = new Address(addressId, address, address2, cityId, postalCode, phone, createDateCalendar, createdBy,lastUpdateCalendar, lastUpdateBy); //get the user info
                addresses.add(userResult); //add everything to the table
                
            }                    
        return addresses;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    } 
    
    
    
// ----------------------------------------------------- get and set
    public static int getAddressId(String addressStreet, int cityId, String postalCode)  {
        try{
            String sql = "SELECT addressId FROM address\n" +
                        "WHERE address = ? AND cityId = ? AND postalCode = ?;";
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setString(1, addressStreet);
            ps.setInt(2, cityId);
            ps.setString(3, postalCode);
            
            ps.execute();                
            
            ResultSet result = ps.getResultSet();            
            if(result.next() == false) {
                System.out.println("Address ResultSet is empty");                
            }
            else {
                int addressId = result.getInt("addressId");            
                System.out.println("After result set....." + addressId);
                                    
                return addressId;
            }

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Going to return -1 for addressId");                                            
        return -1;
    }
    
    public static void setAddressId(String userName, String addressStreet, String addressState, int cityId, String postalCode, String phone)  {
        try{
            String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)\n" +
"                         VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();

            String currentDate = Time.currentUTCDate();

            ps.setString(1, addressStreet);
            ps.setString(2, addressState);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setString(6, currentDate);
            ps.setString(7, userName);
            ps.setString(8, userName);

System.out.println("in setAddressId.........");                        
            ps.execute();                
System.out.println("after execute setAddressId.........");

        }

        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    } 
    
    // make another one overloaded with address2 also set....
       
    
    
    
}
