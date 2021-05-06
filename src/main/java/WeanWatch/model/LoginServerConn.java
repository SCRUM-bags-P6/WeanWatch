package WeanWatch.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class LoginServerConn extends Server{
     private static final String DATABASE_URL = "https://db.course.hst.aau.dk/phpmyadmin/";
     private static final String DATABASE_USERNAME = "hst_2021_21gr6401";
     private static final String DATABASE_PASSWORD = "uowuteiwaiphaxaekeeb";
     private static final String SELECT_QUERY = "SELECT * FROM loginserver WHERE id = ? and password = ?";


    // Store an instance of the object, that can be shared
    private static LoginServerConn instance = null;


    // Set the constructor private
    private LoginServerConn() {
        // Call the super constructor
        super(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }


    // Create the method to get the shared instance
    public static LoginServerConn getInstance() {
        // Create a instance if none has been created
        if (LoginServerConn.instance == null) {
            // Create an instance and store it
            LoginServerConn.instance = new LoginServerConn();
        }
        // Return the stored instance
        return LoginServerConn.instance;
    }


    // Create the method for authenticating user login
    public boolean authenticateUser(String userid, String password) throws SQLException {
        PreparedStatement pstmt = this.getPreparedStatement(SELECT_QUERY);
        pstmt.setString(1, userid);
        pstmt.setString(2, password);
        
        ResultSet resultSet = this.executeQuery(pstmt);
            if (resultSet.next()) {
                return true;
            }
        return false;
    }
    
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
 