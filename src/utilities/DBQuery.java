/*
    CHRISTOPHER BROWN
    C195 ADVANCED JAVA CONCEPTS
 */
package utilities;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author brown
 */
public class DBQuery {

    //Statement referenece
    private static PreparedStatement statement;

    // Sets preparestatement
    public static void SetPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    // Create prepare statement object and executes 
    public static void ExecutePreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
        statement.execute();
    }

    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

}
