package WeanWatch.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Server {

    //Attributes
    private String adresse;
    private String username;
    private String password;

    private Connection conn = null;

    //Constructor
    protected Server(String adresse, String username, String password) {
        this.adresse = adresse;
        this.username = username;
        this.password = password;
    }

    protected Connection getConnection() {
        Connection myConnection = null;
        try {
            // Try loading MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            myConnection = DriverManager.getConnection(this.adresse, this.username, this.password);
        }
        catch (ClassNotFoundException ex) {
            // Print out exception
            System.out.println("Class not found: " + ex.getMessage());
        }
        catch (SQLException sqlex) {
            System.out.println("Connection Error: " + sqlex.getMessage());
        }
        return myConnection;
    }

    protected PreparedStatement getPreparedStatement(String query) {
        try {
            return this.getConnection().prepareStatement(query);
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        }   
    }

    protected ResultSet executeQuery(PreparedStatement pstmt) {
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        }
        catch (SQLException ex) {
            // Handle errors
            System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }
}
