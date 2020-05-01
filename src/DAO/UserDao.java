/*
    CHRISTOPHER BROWN
    C195 ADVANCED JAVA CONCEPTS
 */
package DAO;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utilities.DBConnection;
import utilities.DBQuery;
import static utilities.Time.stringToCalendar;

/**
 *
 * @author brown
 */
public class UserDao {

    static boolean isActive;

    private static Connection connection = DBConnection.getConnection();

    //Gets info for loggin in
    public static ObservableList<User> getUsernamesPasswords() {
        try {
            String sql = "SELECT userName, password FROM user;";
            DBQuery.ExecutePreparedStatement(connection, sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet result = ps.getResultSet();
            User userResult;
            ObservableList<User> userNamesPasswords = FXCollections.observableArrayList();
            while (result.next()) {

                String userName = result.getString("userName");
                String password = result.getString("password");

                userResult = new User(userName, password); //get the user info
                userNamesPasswords.add(userResult); //add everything to the table

            }
            return userNamesPasswords;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static ObservableList<User> getAllUsers() { //Gets info for loggin in

        try {
            String sql = "SELECT * FROM user;";
            DBQuery.ExecutePreparedStatement(connection, sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet result = ps.getResultSet();
            User userResult;
            ObservableList<User> users = FXCollections.observableArrayList();
            while (result.next()) {

                int userId = result.getInt("userId");
                String userName = result.getString("userName");
                String password = result.getString("password");
                int active = result.getInt("active"); //this comes in as an integer 1 for true 0 for false
                if (active == 1) {
                    isActive = true;
                }
                String createDate = result.getString("createDate");
                Calendar createDateCalendar = stringToCalendar(createDate);
                String createdBy = result.getString("createdBy");
                String lastUpdate = result.getString("lastUpdate");
                Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
                String lastUpdateBy = result.getString("lastUpdateBy");

                userResult = new User(userId, userName, password, isActive, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info
                users.add(userResult); //add everything to the table

            }
            return users;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // used to pass user Id to main screen
    public static User getLoggedInUser(String uName, String uPassword) {
        try {
            String sql = "SELECT * FROM user\n"
                    + "WHERE userName = ? AND  password = ?;";

            DBQuery.SetPreparedStatement(connection, sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            ps.setString(1, uName);
            ps.setString(2, uPassword);
            ResultSet result = ps.executeQuery();
            User userResult;

            result.next();
            int userId = result.getInt("userId");
            String userName = result.getString("userName");
            String password = result.getString("password");
            int active = result.getInt("active"); //this comes in as an integer 1 for true 0 for false
            if (active == 1) {
                isActive = true;
            }
            String createDate = result.getString("createDate");
            Calendar createDateCalendar = stringToCalendar(createDate);
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
            String lastUpdateBy = result.getString("lastUpdateBy");;

            userResult = new User(userId, userName, password, isActive, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateBy); //get the user info

            return userResult;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static String getUserName(int userId) {
        try {
            String sql = "SELECT userName FROM user\n"
                    + "WHERE userId = ?;";

            DBQuery.SetPreparedStatement(connection, sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, userId);

            ResultSet result = ps.executeQuery();
            if (result.next() == false) {
                //System.out.println("UserName result is empty");                
            } else {

                String userName = result.getString("userName");

                return userName;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}
