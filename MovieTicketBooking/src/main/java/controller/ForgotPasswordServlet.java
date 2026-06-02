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

		String username = request.getParameter("username");
		String email = request.getParameter("email");

		//  bỏ khoảng trắng
		if (username != null) username = username.trim();
		if (email != null) email = email.trim();

		//1. Kiểm tra dữ liệu đầu vào
		if (username == null || username.isBlank()) {
			sendJsonResponse(response, "error", "Vui lòng nhập tên đăng nhập!");
			return;
		}
		if (!username.matches("^[a-z0-9]+$")) {
			sendJsonResponse(response, "error", "Tên đăng nhập chỉ gồm chữ thường và số.");
			return;
		}

		// 2. Kiểm tra tài khoản tồn tại không
		IUserDAO dao = new UserDAO();
		User user = dao.checkUser(username);
		if (user == null) {
			sendJsonResponse(response, "error", "Tên đăng nhập không tồn tại!");
			return;
		}

		// 3. Kiểm tra email
		if (email == null || email.isBlank()) {
			sendJsonResponse(response, "error", "Email không được để trống.");
			return;
		}
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			sendJsonResponse(response, "error", "Email không hợp lệ.");
			return;
		}

		// 4. Kiểm tra email nhập vào có trùng khớp với email đăng ký của tài khoản không
		if (!user.getEmail().equalsIgnoreCase(email)) {
			sendJsonResponse(response, "error", "Email không khớp với email đăng ký của tài khoản!");
			return;
		}

		// 5. Tạo mã OTP
		String otp = EmailUtils.generateOTP();

		// 6. Gửi Email OTP
		try {
			EmailUtils.sendEmail(email, "Mã xác thực quên mật khẩu - MyCinema", "Mã OTP của bạn là: " + otp);
		} catch (Exception e) {
			e.printStackTrace();
			sendJsonResponse(response, "error", "Không thể gửi email xác thực. Vui lòng thử lại sau!");
			return;
		}

		// 7. Lưu OTP và Email vào Session
		HttpSession session = request.getSession();
		session.setAttribute("otp", otp);
		session.setAttribute("resetEmail", email);
		session.setAttribute("resetUsername", username);
		session.setMaxInactiveInterval(300); // Hết hạn sau 5 phút

		// 8. Trả về thành công và dẫn đường tới verify-otp
		sendJsonResponse(response, "success", "verify-otp");
	}

	// Hàm phụ trợ gửi phản hồi JSON về cho client
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