package controller;

import dao.IUserDAO;
import dao.UserDAO;
import model.User;
import utils.EmailUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest; // Đã dùng đúng tên
import jakarta.servlet.http.HttpServletResponse; // Đã dùng đúng tên
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/forgot-password.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username").trim();
		String email = request.getParameter("email").trim();
		
		if(username == null || username.isBlank()) {
        	backToPage("Vui lòng nhập tên đăng nhập!", request, response);
            return;
        }
        if(!username.matches("^[a-z0-9]+$")) {
        	backToPage("Tên đăng nhập chỉ gồm chữ thường và số.", request, response);
            return;
        }
        // check username is exist
        IUserDAO dao = new UserDAO();
        User user = dao.checkUser(username);
        if (user == null) {
            backToPage("Tên đăng nhập không tồn tại!", request, response);
            return;
        }
        
        // check email
        if (email == null || email.isBlank()) {
            backToPage("Email không được để trống.", request, response);
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            backToPage("Email không hợp lệ.", request, response);
            return;
        }
		
        // check email's user is correct
		if (!user.getEmail().equals(email)) {
			backToPage("Email đã nhập không trùng khớp với email tài khoản!", request, response);
			return;
		} 
		// 1. Tạo OTP
		String otp = EmailUtils.generateOTP();

		// 2. Gửi Email (Có thể mất vài giây)
        try {
            EmailUtils.sendEmail(email, "Mã xác thực quên mật khẩu - MyCinema", "Mã OTP của bạn là: " + otp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 3. Lưu OTP và Email vào Session
		HttpSession session = request.getSession();
		session.setAttribute("otp", otp);
		session.setAttribute("resetEmail", email);
		session.setAttribute("resetUsername", username);
		session.setMaxInactiveInterval(300); // OTP hết hạn sau 5 phút

		// 4. Chuyển hướng sang trang nhập OTP
		response.sendRedirect("verify-otp");

	}

	private void backToPage(String message, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("error", message);
			request.getRequestDispatcher("/WEB-INF/view/forgot-password.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}