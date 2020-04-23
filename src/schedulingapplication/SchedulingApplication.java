/*
 * This starts the application and opens the Login Window
 */
package schedulingapplication;

import DAO.UserDao;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import static java.util.Locale.getDefault;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.LanguageConversion;

/**
 *
 * @author Christopher Brown
 */
public class SchedulingApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/LoginScreen.fxml"));
        primaryStage.setTitle("User Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
       
        
        
        
        

        //starts the Database connection
        Connection connection = DBConnection.startConnection();
       
        
        // Anything before launch starts when project opens
        launch(args);
        // Anything after launch executes when all the windows are closed
        DBConnection.closeConnection();
    }
    
}

       

