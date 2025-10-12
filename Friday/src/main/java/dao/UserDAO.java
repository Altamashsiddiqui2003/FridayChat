package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import models.User;

public class UserDAO {
	
	// Create A User Method 
	public static boolean createUser(String username, String email, String password) throws SQLException {
        String hash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        String sql = "INSERT INTO users(username,email,password_hash) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) 
        {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, hash);
            ps.executeUpdate();
            return true;
        } 
        catch (SQLIntegrityConstraintViolationException e)
        {
            return false; // username/email exists
        }
    }
	
	// This Method works for Find users on Login proccess
	
	public static User findByUsername(String username) throws SQLException {
        String sql = "SELECT id,username,email,password_hash FROM users WHERE username=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setEmail(rs.getString("email"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    return u;
                }
            }
        }
        return null;
    }
	
	// aunthitactce  validtion method 
	 public static User authenticate(String username, String password) throws SQLException {
	        User u = findByUsername(username);
	        if (u == null) return null;
	        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), u.getPasswordHash());
	        return result.verified ? u : null;
	    }

	
	
}
