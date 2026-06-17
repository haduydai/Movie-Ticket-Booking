package controller;

import dao.IUserDAO;
import dao.UserDAO;
import model.User;
import utils.EmailUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

		String username = request.getParameter("username");
		String email = request.getParameter("email");


		if (username != null) username = username.trim();
		if (email != null) email = email.trim();


		if (username == null || username.isBlank()) {
			sendJsonResponse(response, "error", "Vui lòng nhập tên đăng nhập!");
			return;
		}
		if (!username.matches("^[a-z0-9]+$")) {
			sendJsonResponse(response, "error", "Tên đăng nhập chỉ gồm chữ thường và số.");
			return;
		}


		IUserDAO dao = new UserDAO();
		User user = dao.checkUser(username);
		if (user == null) {
			sendJsonResponse(response, "error", "Tên đăng nhập không tồn tại!");
			return;
		}


		if (email == null || email.isBlank()) {
			sendJsonResponse(response, "error", "Email không được để trống.");
			return;
		}
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			sendJsonResponse(response, "error", "Email không hợp lệ.");
			return;
		}


		if (!user.getEmail().equalsIgnoreCase(email)) {
			sendJsonResponse(response, "error", "Email không khớp với email đăng ký của tài khoản!");
			return;
		}


		String otp = EmailUtils.generateOTP();


		try {
			EmailUtils.sendEmail(email, "Mã xác thực quên mật khẩu - MyCinema", "Mã OTP của bạn là: " + otp);
		} catch (Exception e) {
			e.printStackTrace();
			sendJsonResponse(response, "error", "Không thể gửi email xác thực. Vui lòng thử lại sau!");
			return;
		}


		HttpSession session = request.getSession();

		session.removeAttribute("newUser");

		session.setAttribute("otp", otp);
		session.setAttribute("resetEmail", email);
		session.setAttribute("resetUsername", username);
		session.setMaxInactiveInterval(300);


		sendJsonResponse(response, "success", "verify-otp");
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