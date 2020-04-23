/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author brown
 */
public class DBQuery {
    
    //Statement referenece
    private static PreparedStatement statement;
    
    // Sets preparestatement
    public static void SetPreparedStatement(Connection connection, String sqlStatement) throws SQLException{        
        statement = connection.prepareStatement(sqlStatement);
    }    
    
    // Create prepare statement object and executes 
    public static void ExecutePreparedStatement(Connection connection, String sqlStatement) throws SQLException{        
        statement = connection.prepareStatement(sqlStatement);
        statement.execute();
    }
    
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
    
}
