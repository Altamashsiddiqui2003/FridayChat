package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String URL = "jdbc:mysql://localhost:3306/Friday";
    private static final String USER = "root";
    private static final String PASS = "root"; 
    
    // Driver Loader 
    static {
    	
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } 
        catch (Exception e) {
        	
        	 System.out.println("Error"+e);
        	 
        	}
        
    }
    // static Method returns a Connection 
    public static Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(URL, USER, PASS);
    }

}
