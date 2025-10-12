<%@ page import="models.User,java.util.List,models.ChatMessage,dao.ChatDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // --- Session & authentication check ---
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    User u = (User) sess.getAttribute("user");
    String username = (u != null && u.getUsername() != null) ? u.getUsername() : "User";

    // --- Fetch last 20 messages ---
    List<ChatMessage> messages = null;
    try { messages = ChatDAO.getMessagesForUser(u.getId(), 20); } catch (Exception e) { e.printStackTrace(); }
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Friday Chat Assistant</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body, html { height: 100%; margin: 0; padding: 0; }
    .chat-box { overflow-y: auto; flex-grow: 1; padding: 10px; }
    .user-bubble {
        background-color: #0d6efd; color: white;
        border-radius: 15px 15px 0 15px; padding: 10px;
        max-width: 80%; word-wrap: break-word;
    }
    .assistant-bubble {
        background-color: #e9ecef; color: black;
        border-radius: 15px 15px 15px 0; padding: 10px;
        max-width: 80%; word-wrap: break-word;
    }
    .chat-container { display: flex; flex-direction: column; height: 80vh; }
    .chat-input { padding: 10px; }
    .chat-msg { margin-bottom: 10px; }
</style>
</head>
<body class="d-flex flex-column">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary sticky-top">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold" href="#">Friday Chat</a>
    <div class="d-flex ms-auto align-items-center">
      <span class="text-white me-3 d-none d-md-inline">Welcome, <strong><%= username %></strong></span>
      <a href="logout" class="btn btn-danger">Logout</a>
    </div>
  </div>
</nav>

<!-- Chat container -->
<div class="container-fluid chat-container d-flex flex-column mt-3">
    <!-- Chat messages -->
    <div id="chatBox" class="chat-box">
        <% if (messages != null) {
            for (int i = messages.size() - 1; i >= 0; i--) {
                ChatMessage m = messages.get(i);
                String role = m.getRole();
        %>
        <div class="chat-msg d-flex <%= role.equals("user") ? "justify-content-end" : "justify-content-start" %>">
            <div class="<%= role.equals("user") ? "user-bubble" : "assistant-bubble" %>">
                <strong class="small d-block mb-1"><%= role.equals("user") ? "You" : "Friday" %></strong>
                <p class="mb-0" style="white-space: pre-wrap;"><%= m.getMessage() %></p>
            </div>
        </div>
        <% } } %>
    </div>

    <!-- Input box -->
    <form method="post" action="chat" class="chat-input d-flex">
        <textarea name="prompt" class="form-control me-2" rows="1" placeholder="Ask Friday anything..." required></textarea>
        <button type="submit" class="btn btn-primary">Send</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Scroll chat to bottom on load
    function scrollToBottom() {
        const chatBox = document.getElementById('chatBox');
        chatBox.scrollTop = chatBox.scrollHeight;
    }
    window.onload = scrollToBottom;
</script>

</body>
</html>
