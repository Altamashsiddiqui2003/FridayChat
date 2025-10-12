package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.ChatMessage;

public class ChatDAO {
	
	// saves Chat into DataBase
	
	public static void saveMessage(int userId, String role, String message) throws SQLException {
		
        String sql = "INSERT INTO chat_messages(user_id, role, message) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, role);
            ps.setString(3, message);
            ps.executeUpdate();
        }
    }
	
	
	public static List<ChatMessage> getMessagesForUser(int userId, int limit) throws SQLException {
        String sql = "SELECT id,role,message,created_at FROM chat_messages WHERE user_id=? ORDER BY created_at DESC LIMIT ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                List<ChatMessage> list = new ArrayList<>();
                while (rs.next()) {
                    ChatMessage m = new ChatMessage();
                    m.setId(rs.getInt("id"));
                    m.setRole(rs.getString("role"));
                    m.setMessage(rs.getString("message"));
                    m.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(m);
                }
                return list;
            }
        }
    }

}
