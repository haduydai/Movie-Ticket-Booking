package controller;

import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import utils.PasswordUtils;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        // Nếu chưa xác thực email (chưa có resetEmail trong session) thì đá về login
        if (session.getAttribute("resetEmail") == null) {
            response.sendRedirect("login"); 
            return;
        }
        request.getRequestDispatcher("/WEB-INF/view/reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String newPass = request.getParameter("newPass");
        String confirmPass = request.getParameter("confirmPass");

        if (newPass == null || newPass.isBlank() || confirmPass == null || confirmPass.isBlank()) {
            sendJsonResponse(response, "error", "Mật khẩu không được để trống!");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            sendJsonResponse(response, "error", "Mật khẩu xác nhận không khớp!");
            return;
        }

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("resetUsername");

        if (username == null) {
            sendJsonResponse(response, "error", "Phiên làm việc đã hết hạn. Vui lòng thực hiện lại từ đầu!");
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.checkUser(username);

        if (user == null) {
            sendJsonResponse(response, "error", "Tài khoản không tồn tại!");
            return;
        }

        //mã hóa và cập nhật mật khẩu mới
        try {
            user.setPassword(PasswordUtils.hashPassword(newPass));
            dao.updateUser(user);

            //Xóa session OTP và thông tin reset để bảo mật
            session.removeAttribute("otp");
            session.removeAttribute("resetUsername");
            session.removeAttribute("resetEmail");

            // trả về JSON thành công, gửi kèm hướng đi kế tiếp
            sendJsonResponse(response, "success", "login");
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, "error", "Có lỗi xảy ra khi cập nhật mật khẩu. Vui lòng thử lại!");
        }
    }
    private void sendJsonResponse(HttpServletResponse response, String status, String message) {
        try {
            response.setContentType("application/json; charset=UTF-8");
            String json = String.format("{\"status\":\"%s\",\"message\":\"%s\"}", status, message);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}