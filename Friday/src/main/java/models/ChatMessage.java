package models;


import java.sql.Timestamp;

public class ChatMessage {
    private int id;
    private int userId;
    private String role;      
    private String message;   
    private Timestamp createdAt; 

    
    public ChatMessage() {
    	
    }

    
    public ChatMessage(int id, int userId, String role, String message, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.message = message;
        this.createdAt = createdAt;
    }

    // Constructor for inserting new chat message
    public ChatMessage(int userId, String role, String message) {
        this.userId = userId;
        this.role = role;
        this.message = message;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    //  Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    //  Just for Debuing a projrct if required
    
    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
