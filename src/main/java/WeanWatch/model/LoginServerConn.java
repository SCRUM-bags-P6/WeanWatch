package WeanWatch.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class LoginServerConn extends Server{
     private static final String DATABASE_URL = "";
     private static final String DATABASE_USERNAME = "";
     private static final String DATABASE_PASSWORD = "";
     private static final String SELECT_QUERY = "";
     //private static final String INSERT_QUERY = "";

    public boolean validateUser(String userName, String userPass) throws SQLException {
        //Establish connection
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPass);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            return true;
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    }
        

    /*
    Add user
    public void insertPersonelData(String userName, String userPass) throws SQLException {
        // Establish connection
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPass);

            System.out.println(preparedStatement);
            // Update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
    }
    */

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }    
}
 