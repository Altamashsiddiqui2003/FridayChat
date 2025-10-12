package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ChatDAO;
import models.User;
import util.HttpUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ObjectMapper mapper = new ObjectMapper();

    // Gemini API endpoint
    private final String GEMINI_CHAT_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String prompt = req.getParameter("prompt");
        if (prompt == null || prompt.trim().isEmpty()) {
            resp.sendRedirect("chat.jsp");
            return;
        }
        prompt = prompt.trim();

        String assistantText = "";

        // ✅ Fixed answer for "who created you" questions
        if (prompt.toLowerCase().contains("who created you") ||
            prompt.toLowerCase().contains("who made you") ||
            prompt.toLowerCase().contains("your creator")) {

            assistantText = "Altamash Siddiqui";

        } else {
            try {
                // 🗣 Save user message
                ChatDAO.saveMessage(user.getId(), "user", prompt);

                // ✅ Build request body for Gemini API
                Map<String, Object> requestBody = new HashMap<>();
                Map<String, Object> part = Map.of("text", prompt);
                Map<String, Object> content = Map.of("parts", List.of(part));
                requestBody.put("contents", List.of(content));

                String requestJson = mapper.writeValueAsString(requestBody);

                // 🌐 Call Gemini API
                String apiResponse = HttpUtil.postJson(GEMINI_CHAT_URL, requestJson);

                // 🧠 Parse AI response
                JsonNode root = mapper.readTree(apiResponse);
                JsonNode candidates = root.path("candidates");
                if (candidates.isArray() && candidates.size() > 0) {
                     assistantText = candidates
                        .path(0)
                        .path("content")
                        .path("parts")
                        .path(0)
                        .path("text")
                        .asText();
                } else {
                    JsonNode errorNode = root.path("error");
                    if (!errorNode.isMissingNode()) {
                        assistantText = "Error from API: " + errorNode.path("message").asText();
                    } else {
                        assistantText = "Could not parse API response or received no content.";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                assistantText = "API error: " + e.getMessage();
            }
        }

        // 💾 Save assistant reply
        try {
			ChatDAO.saveMessage(user.getId(), "assistant", assistantText);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 🪄 Show output on JSP
        req.setAttribute("assistantText", assistantText);
        req.getRequestDispatcher("chat.jsp").forward(req, resp);
    }
}
