package controller;

import dao.UserDAO;
import model.Role;
import model.User;
import utils.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        // Validate username
        if (username == null || username.isBlank()) {
            backToPage("Vui lòng nhập tên đăng nhập!", request, response);
            return;
        }
        if (!username.matches("^[a-z0-9]+$")) {
            backToPage("Tên đăng nhập chỉ gồm chữ thường và số.", request, response);
            return;
        }
        if (username.length() < 3 || username.length() > 30) {
            backToPage("Tên đăng nhập phải có độ dài 3-30 kí tự.", request, response);
            return;
        }
        // Validate password
        if (pass == null || pass.isEmpty()) {
            backToPage("Mật khẩu không được để trống.", request, response);
            return;
        }
        if (pass.length() > 40) {
            backToPage("Mật khẩu tối đa 40 kí tự.", request, response);
            return;
        }
        if (!pass.matches("^[a-z0-9]+$")) {
            backToPage("Mật khẩu chỉ được chứa chữ thường và số.", request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.checkUser(username);
        if (user == null) {
            backToPage("Tài khoản không tồn tại.", request, response);
            return;
        }

        // Check locked status
        if (user.isLocked()) {
            backToPage("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên.", request, response);
            return;
        }

        if (PasswordUtils.checkPassword(pass, user.getPassword())) {
            HttpSession session = request.getSession();

            // Role-based redirect
            if (Role.ADMIN.equals(user.getRole())) {
                // Admin: store in "admin" key and redirect to admin dashboard
                session.setAttribute("admin", user);
                session.setAttribute("user", user);
                sendJson(response, "success", "admin/dashboard");
            } else {
                // Regular user: store in "user" key and redirect to home
                session.setAttribute("user", user);
                sendJson(response, "success", "home");
            }
        } else {
            backToPage("Sai mật khẩu! Vui lòng thử lại.", request, response);
        }
    }

    private void backToPage(String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            sendJson(response, "error", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendJson(HttpServletResponse response, String status, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\": \"" + status + "\", \"message\": \"" + message + "\"}");
    }
}