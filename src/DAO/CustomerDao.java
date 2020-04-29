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
import model.Customer;
import model.User;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.Time;
import static utilities.Time.stringToCalendar;

/**
 *
 * @author brown
 */
public class CustomerDao {
    
    private static Connection connection = DBConnection.getConnection();
    
    public static ObservableList<Customer> getAllCustomers() { //Gets info for loggin in
        
        try{
            String sql = "SELECT * FROM customer;";
            DBQuery.ExecutePreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ResultSet result = ps.getResultSet();            
            Customer userResult;
            ObservableList<Customer> customers = FXCollections.observableArrayList();    
            while(result.next()){
                
                int customerId = result.getInt("customerId");;               
                String customerName = result.getString("customerName");
                int addressId = result.getInt("addressId");
                int active = result.getInt("active"); //this comes in as an integer 1 for true 0 for false
                if(active == 1) {
                    isActive = true;
                }
                else{
                    isActive = false;
                }
                String createDate = result.getString("createDate");
                Calendar createDateCalendar=stringToCalendar(createDate);                
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);                                
                String lastUpdateBy = result.getString("lastUpdateBy");;               
                
                userResult = new Customer(customerId, customerName, addressId, isActive, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                customers.add(userResult); //add everything to the table
                                
            }  
            
            
        return customers;

        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }    
    
    
// --------------------------------------SET CUSTOMERS --------------------------------------------    
        public static void addCustomer(String userName, String customerName, String phone, String street, String city, String state, String country, String zip, int active) { //Inserts into DB
        
        try{
            System.out.println("Starting the AddCustomer function");
            
            
             String sql = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy)\n" +
                          "VALUES(?,?,?,?,?,?);";
             
            
            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            

            String currentDate = Time.currentUTCDate();
            
            int addressId = fullAddressToAddressId(userName, phone, street, city, state, country, zip);
            
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setShort(3, (short) active);
            ps.setString(4, currentDate);
            ps.setString(5, userName);
            ps.setString(6, userName);
                                   
            ps.execute();
            
            
            System.out.println("Ending the AddCustomer function");
            
                                             
        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
    }    
    
    private static int fullAddressToAddressId(String userName, String phone, String street, String city, String state, String country, String zip) {
        int countryId;
        int cityId;
        int addressId;
        
        countryId = CountryDao.getCountryId(country);
        
        if(countryId <= 0){
            CountryDao.setCountryId(userName, country);
            countryId = CountryDao.getCountryId(country);
                        System.out.println("Country Id after if : " + countryId);
            
        }
        
        cityId = CityDao.getCityId(city, countryId);
        if(cityId <=0){
            CityDao.setCityId(userName, city, countryId);
            cityId = CityDao.getCityId(city, countryId);
                        System.out.println("City Id after if : " + countryId);
            
        }
            
        addressId = AddressDao.getAddressId(street, state, cityId, zip, phone);
        if(addressId <=0){
            AddressDao.setAddressId(userName, street, state, cityId, zip, phone);
            addressId = AddressDao.getAddressId(street, state, cityId, zip, phone);
                        System.out.println("Address Id after if : " + addressId);            
        }
        
        
        return addressId;
    }
    
    
    
    
    
// -------------------------------- Delete ---------------------------------
    
    public static boolean deleteSelectedCustomer(Customer selectedCustomer) {

        try {
            
        
        String sql = "DELETE FROM customer WHERE customerId = ?;";

        DBQuery.SetPreparedStatement(connection, sql);           
        PreparedStatement ps = DBQuery.getPreparedStatement();        
        
        int customerId = selectedCustomer.getCustomerId();
        
        ps.setInt(1, customerId);
        
        ps.execute();
        
        return true;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        return false;
    }
    
    
  // --------------------------------- Update ---------------------------------
    
    public static void updateCustomer(int selectedCustomerId, String userName, String customerName, String phone, String street, String city, String state, String country, String zip, int active) {

        try {
            
               
        String sql = "UPDATE customer\n" +
                     "SET customerName = ? , addressId = ?, active = ?, lastUpdateBy = ?\n" +
                     "WHERE customerId = ?";
        
        DBQuery.SetPreparedStatement(connection, sql);           
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        int addressId = fullAddressToAddressId(userName, phone, street, city, state, country, zip);
        
        ps.setString(1, customerName);
        ps.setInt(2, addressId);
        ps.setShort(3, (short) active);
        ps.setString(4, userName);
        ps.setInt(5, selectedCustomerId);

        ps.execute();
        
        }
        catch(Exception e) {
            System.out.println(e);
        }        
    }
    
    

    public static String getCustomerName(int customerId){

        try {
            String sql = "SELECT customerName\n" +
                         "FROM customer\n" +
                         "WHERE customerId = ?;";

            DBQuery.SetPreparedStatement(connection, sql);           
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, customerId);

            ps.execute();
            
            ResultSet result = ps.getResultSet();            
            if(result.next() == false) {
                System.out.println("CustomerName result is empty");                
            }
            else {
                String name = result.getString("customerName");            
                                    
                return name;
            }                    
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
}
