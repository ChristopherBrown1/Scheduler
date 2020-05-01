/*
    CHRISTOPHER BROWN
    C195 ADVANCED JAVA CONCEPTS
 */
package schedulingapplication;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DBConnection;

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
