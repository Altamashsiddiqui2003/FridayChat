package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import models.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    try {
      User u = UserDAO.authenticate(username, password);
      if (u == null) {
        req.setAttribute("error", "Invalid credentials.");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
        return;
      }
      HttpSession session = req.getSession(true);
      session.setAttribute("user", u);
      resp.sendRedirect("chat.jsp");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("error", "Server error.");
      req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
  }
}
