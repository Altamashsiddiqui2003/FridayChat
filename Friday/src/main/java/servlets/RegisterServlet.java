package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String username = req.getParameter("username");
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    try {
      boolean ok = UserDAO.createUser(username, email, password);
      if (!ok)
      {
        req.setAttribute("error", "Username or email already used.");
        req.getRequestDispatcher("register.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect("login.jsp");
    } 
    catch (Exception e)
    {
      e.printStackTrace();
      req.setAttribute("error", "Server error: " + e.getMessage());
      req.getRequestDispatcher("register.jsp").forward(req, resp);
    }
  }
}
