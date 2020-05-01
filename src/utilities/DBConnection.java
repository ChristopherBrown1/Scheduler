/*
    CHRISTOPHER BROWN
    C195 ADVANCED JAVA CONCEPTS
 */
package utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author brown
 */
public class DBConnection {

    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    //server name followed by database name
    private static final String ipAddress = "//3.227.166.251/U05X3p";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    // Driver  and Connectioninterface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection connection = null;

    private static final String username = "U05X3p";
    private static final String password = "53688631529";

    public static Connection startConnection() {

        try {
            // searches project directory for the driver
            Class.forName(MYSQLJDBCDriver);
            connection = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            //System.out.println("Connection successful");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            //System.out.println("Connection closed");            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Connection getConnection() {
        return connection;
    }

}
