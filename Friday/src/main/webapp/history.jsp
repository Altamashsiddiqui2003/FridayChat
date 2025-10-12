<%@ page import="java.util.*,models.ChatMessage,dao.ChatDAO,models.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    User u = (User) sess.getAttribute("user");
    List<ChatMessage> messages = ChatDAO.getMessagesForUser(u.getId(), 100);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Chat History</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
</head>
<body class="bg-light">
<div class="container mt-5">
    <h3>Your Chat History</h3>
    <a href="chat.jsp" class="btn btn-secondary mb-3">← Back</a>
    <div class="card p-3 shadow">
        <% for (int i = messages.size()-1; i >= 0; i--) { 
            ChatMessage m = messages.get(i); %>
            <p><strong><%= m.getRole() %>:</strong> <%= m.getMessage() %></p>
        <% } %>
    </div>
</div>
</body>
</html>
